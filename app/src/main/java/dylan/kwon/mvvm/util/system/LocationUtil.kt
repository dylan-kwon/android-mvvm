package dylan.kwon.mvvm.util.system

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import dylan.kwon.mvvm.util.LogUtil
import dylan.kwon.mvvm.util.extension.timer
import io.reactivex.disposables.Disposable
import java.lang.ref.SoftReference
import java.util.concurrent.atomic.AtomicBoolean


class LocationUtil(applicationContext: Context) {

    companion object {

        @JvmField
        val TAG: String = LocationUtil::class.java.simpleName

        const val WAITING_TIME: Long = 10_000
        const val PROVIDER_DISABLED_ERROR_MESSAGE: String = "gps is disabled."
    }

    private val instance: LocationManager =
        applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    /**
     * GPS 사용 가능 여부
     * 정확도가 비교적 높으나 실내에서 사용할 수 없다.
     */
    val isGpsEnabled: Boolean
        get() = instance.isProviderEnabled(LocationManager.GPS_PROVIDER)

    /**
     * NETWORK 사용 가능 여부
     * 정확도가 비교적 낮으나 실내에서 사용할 수 있다.
     */
    val isNetworkEnabled: Boolean
        get() = instance.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    private var timerDisposable: Disposable? = null

    private var locationListener: LocationListener? = null
    private var softRefOnSuccessListener: SoftReference<(location: Location) -> Unit>? = null

    private var softRefOnErrorListener: SoftReference<(errorMessage: String) -> Unit>? = null

    private val isLocationResponse: AtomicBoolean = AtomicBoolean(false)

    /**
     * 현재 위치 구하기.
     */
    @SuppressLint("MissingPermission")
    fun getLocation(
        waiting: Long = WAITING_TIME,
        onSuccess: (location: Location) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        clear()

        if (!isGpsEnabled && !isNetworkEnabled) {
            onError(PROVIDER_DISABLED_ERROR_MESSAGE)
            return
        }
        isLocationResponse.set(false)

        softRefOnSuccessListener = SoftReference(onSuccess)
        softRefOnErrorListener = SoftReference(onError)

        locationListener = object : LocationListener {

            /**
             * 위치가 변경되면 호출됨.
             */
            override fun onLocationChanged(location: Location?) {
                if (isLocationResponse.get()) {
                    return
                }
                isLocationResponse.compareAndSet(false, true)

                location?.let {
                    softRefOnSuccessListener?.get()?.let { onSuccess ->
                        onSuccess(it)
                    }
                    LogUtil.d(TAG, "onLocationChanged.provider: ${it.provider}")
                    LogUtil.d(TAG, "onLocationChanged.latitude: ${it.latitude}")
                    LogUtil.d(TAG, "onLocationChanged.longitude: ${it.longitude}")
                }
                clear()
            }

            /**
             * Provider(GPS 혹은 Network)의 상태가 변경되면 호출됨.
             */
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                LogUtil.i(TAG, "onStatusChanged $provider, $status")
            }

            /**
             * Provider(GPS 혹은 Network)가 활성화되면 호출됨.
             */
            override fun onProviderEnabled(provider: String?) {
                LogUtil.i(TAG, "onProviderEnabled $provider")
            }

            /**
             * Provider(GPS 혹은 Network)가 비활성화되면 호출됨.
             */
            override fun onProviderDisabled(provider: String?) {
                LogUtil.i(TAG, "onProviderDisabled $provider")
            }

        }
        val criteria: Criteria = Criteria().apply {
            isCostAllowed = true
            isSpeedRequired = true
            isBearingRequired = false
            isAltitudeRequired = false
            accuracy = Criteria.ACCURACY_LOW
            powerRequirement = Criteria.POWER_LOW
        }

        instance.requestSingleUpdate(
            criteria,
            locationListener!!,
            Looper.myLooper()
        )

        timerDisposable = timer(waiting) {
            when (isLocationResponse.get()) {
                true -> return@timer
                false -> onResponseError()
            }
        }
    }

    private fun onResponseError() {
        softRefOnErrorListener?.get()?.let { onError ->
            onError(PROVIDER_DISABLED_ERROR_MESSAGE)
        }
        clear()
    }

    fun clear() {
        locationListener?.let {
            instance.removeUpdates(it)
        }
        if (timerDisposable?.isDisposed == false) {
            timerDisposable?.dispose()
        }
        softRefOnSuccessListener?.clear()
        softRefOnErrorListener?.clear()
    }

}
