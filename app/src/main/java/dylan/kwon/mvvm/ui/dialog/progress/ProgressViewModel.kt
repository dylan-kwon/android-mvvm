package dylan.kwon.mvvm.ui.dialog.progress

import androidx.lifecycle.LiveData
import dylan.kwon.mvvm.util.base.livedata.StateLiveData
import dylan.kwon.mvvm.util.base.viewmodel.BaseViewModel

class ProgressViewModel : BaseViewModel() {

    private val _dimColor: StateLiveData<Int> = StateLiveData()
    val dimColor: LiveData<Int>
        get() = _dimColor

    fun setDimColor(dimColor: Int) {
        _dimColor.value = dimColor
    }

}
