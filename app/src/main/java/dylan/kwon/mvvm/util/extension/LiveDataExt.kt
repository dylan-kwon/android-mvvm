package dylan.kwon.mvvm.util.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

fun <X, Y> LiveData<X>.map(body: (X?) -> Y?): LiveData<Y> =
    Transformations.map(this, body)

fun <X, Y> LiveData<X>.switchMap(body: (X?) -> LiveData<Y>): LiveData<Y> =
    Transformations.switchMap(this, body)

fun <T> MutableLiveData<T>.setValueIfNew(newValue: T?) {
    if (this.value != newValue) value = newValue
}

fun <T> MutableLiveData<T>.postValueIfNew(newValue: T?) {
    if (this.value != newValue) postValue(newValue)
}
