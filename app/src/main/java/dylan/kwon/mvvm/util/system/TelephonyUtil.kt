package dylan.kwon.mvvm.util.system

import android.content.Context
import android.telephony.TelephonyManager

class TelephonyUtil(val applicationContext: Context) {

    private val instance: TelephonyManager =
        applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    /**
     * @return 현재 연결된 네트워크의 타입
     */
    val isNetworkType: Int
        get() = instance.networkType


    /**
     * @return 2G 연결 여부
     */
    val isConnected2G: Boolean
        get() = when (isNetworkType) {
            TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_IDEN,
            TelephonyManager.NETWORK_TYPE_1xRTT -> true
            else -> false
        }


    /**
     * @return 3G 연결 여부
     */
    val isConnected3G: Boolean
        get() = when (isNetworkType) {
            TelephonyManager.NETWORK_TYPE_UMTS,
            TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_HSUPA,
            TelephonyManager.NETWORK_TYPE_HSDPA,
            TelephonyManager.NETWORK_TYPE_HSPAP,
            TelephonyManager.NETWORK_TYPE_EHRPD,
            TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A,
            TelephonyManager.NETWORK_TYPE_EVDO_B -> true
            else -> false
        }

    /**
     * @return 4G 연결 여부
     */
    val isConnected4G: Boolean
        get() = when (isNetworkType) {
            TelephonyManager.NETWORK_TYPE_LTE -> true
            else -> false
        }

}
