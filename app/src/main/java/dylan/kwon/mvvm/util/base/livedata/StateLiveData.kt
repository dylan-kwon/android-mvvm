package dylan.kwon.mvvm.util.base.livedata

import androidx.lifecycle.MutableLiveData

class StateLiveData<T>(

    value: T? = null

) : MutableLiveData<T>() {

    init {
        value?.let {
            this.value = value
        }
    }

}
