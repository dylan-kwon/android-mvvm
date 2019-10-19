package dylan.kwon.mvvm.util.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dylan.kwon.mvvm.util.base.livedata.EventLiveData
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    @Suppress("PropertyName")
    protected val TAG: String = this.javaClass.simpleName

    private val _showToast: EventLiveData<String> = EventLiveData()
    val showToast: LiveData<String>
        get() = _showToast

    private val _showSnackbar: EventLiveData<String> = EventLiveData()
    val showSnackbar: LiveData<String>
        get() = _showSnackbar

    private val _showProgress: EventLiveData<Unit> = EventLiveData()
    val showProgress: LiveData<Unit>
        get() = _showProgress

    private val _dismissProgress: EventLiveData<Unit> = EventLiveData()
    val dismissProgress: LiveData<Unit>
        get() = _dismissProgress

    private val _showNetworkError: EventLiveData<Unit> = EventLiveData()
    val showNetworkError: LiveData<Unit>
        get() = _showNetworkError

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun showToast(message: String?) {
        _showToast.value = message
    }

    fun showSnackbar(message: String) {
        _showSnackbar.value = message
    }

    fun showProgress() {
        _showProgress.call()
    }

    fun dismissProgress() {
        _dismissProgress.call()
    }

    fun showNetworkError() {
        _showNetworkError.call()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
