package dylan.kwon.mvvm.util.base.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveData<T> : MutableLiveData<T>() {

    private val foreverObservers: MutableList<Observer<in T>> = mutableListOf()

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        removeObservers(owner)
        foreverObservers.add(observer)
        super.observe(owner, observer)
    }

    override fun observeForever(observer: Observer<in T>) {
        removeAllObservers()
        foreverObservers.add(observer)
        super.observeForever(observer)
    }

    private fun removeAllObservers() {
        if (foreverObservers.isNotEmpty()) {
            for (foreverObserver: Observer<in T> in foreverObservers) {
                removeObserver(foreverObserver)
                foreverObservers.remove(foreverObserver)
            }
        }
    }

}
