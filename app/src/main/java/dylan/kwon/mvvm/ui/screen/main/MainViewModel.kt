package dylan.kwon.mvvm.ui.screen.main

import androidx.lifecycle.LiveData
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.util.ResourceProvider
import dylan.kwon.mvvm.util.base.livedata.EventLiveData
import dylan.kwon.mvvm.util.base.viewmodel.BaseViewModel
import dylan.kwon.mvvm.util.extension.timer
import io.reactivex.rxkotlin.plusAssign

class MainViewModel(

    private val resourceProvider: ResourceProvider

) : BaseViewModel() {

    private val _intentRecyclerView: EventLiveData<Unit> = EventLiveData()
    val intentRecyclerView: LiveData<Unit>
        get() = _intentRecyclerView

    private val _intentViewPager: EventLiveData<Unit> = EventLiveData()
    val intentViewPager: LiveData<Unit>
        get() = _intentViewPager

    private val _intentFragment: EventLiveData<Unit> = EventLiveData()
    val intentFragment: LiveData<Unit>
        get() = _intentFragment

    fun intentRecyclerView() {
        _intentRecyclerView.call()
    }

    fun intentViewPager() {
        _intentViewPager.call()
    }

    fun intentFragment() {
        _intentFragment.call()
    }

    fun showDialog() {
        showProgress()
        this.compositeDisposable += timer(3000) {
            dismissProgress()
        }
    }

    fun showBottomSheetDialog() {
        showNetworkError()
    }

    fun showToast() = showToast(resourceProvider.getString(R.string.on_click_toast))

    fun showSnackbar() = showSnackbar(resourceProvider.getString(R.string.on_click_snackbar))

}