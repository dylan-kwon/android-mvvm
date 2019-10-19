package dylan.kwon.mvvm.util.base.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * ViewModel -> View 이벤트 전달을 위한 LiveData.
 * 화면 회전 등 ViewModel의 인스턴스가 파괴되지 않고 그대로 유지된 경우, LiveData에 Observer가 다시 등록될 때 기존에 저장된 값이 그대로 전달되어 이벤트가 중복 발생하는 문제를 방지함.
 *
 * 예) ToastEventLiveData를 생성 후 "Hello" 값을 저장한 경우 화면을 회전하면 Toast가 보여짐을 해결.
 */
class EventLiveData<T> : MutableLiveData<T>() {

    private val lock: AtomicBoolean = AtomicBoolean(false)

    fun call(value: T? = null, isPostUpdate: Boolean = false) {
        when (isPostUpdate) {
            true -> super.postValue(value)
            false -> super.setValue(value)
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            when (lock.get()) {
                true -> lock.compareAndSet(true, false)
                false -> observer.onChanged(it)
            }
        })
    }

    override fun removeObserver(observer: Observer<in T>) {
        super.removeObserver(observer)
        onRemovedObserver()
    }

    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)
        onRemovedObserver()
    }

    private fun onRemovedObserver() {
        value = null
        lock.compareAndSet(false, true)
    }

}
