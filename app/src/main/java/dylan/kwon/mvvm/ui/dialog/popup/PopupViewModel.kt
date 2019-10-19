package dylan.kwon.mvvm.ui.dialog.popup

import androidx.lifecycle.LiveData
import dylan.kwon.mvvm.util.base.livedata.EventLiveData
import dylan.kwon.mvvm.util.base.livedata.StateLiveData
import dylan.kwon.mvvm.util.base.viewmodel.BaseViewModel

class PopupViewModel : BaseViewModel() {

    private val _title: StateLiveData<String> = StateLiveData()
    val title: StateLiveData<String>
        get() = _title

    private val _description: StateLiveData<String> = StateLiveData()
    val description: StateLiveData<String>
        get() = _description

    private val _positive: StateLiveData<String> = StateLiveData()
    val positive: StateLiveData<String>
        get() = _positive

    private var _negative: StateLiveData<String> = StateLiveData()
    val negative: StateLiveData<String>
        get() = _negative

    private val _startPositiveEvent: EventLiveData<Unit> = EventLiveData()
    val startPositiveEvent: LiveData<Unit>
        get() = _startPositiveEvent

    private val _startNegativeEvent: EventLiveData<Unit> = EventLiveData()
    val startNegativeEvent: LiveData<Unit>
        get() = _startNegativeEvent

    fun setTitle(title: String?) {
        _title.value = title
    }

    fun setDescription(description: String?) {
        _description.value = description
    }

    fun setPositive(positive: String?) {
        _positive.value = positive
    }

    fun setNegative(negative: String?) {
        _negative.value = negative
    }

    fun startPositiveEvent() {
        _startPositiveEvent.call()
    }

    fun startNegativeEvent() {
        _startNegativeEvent.call()
        PopupDialog.Extra
    }

}