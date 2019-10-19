package dylan.kwon.mvvm.ui.dialog.popup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.databinding.DialogPopupBinding
import dylan.kwon.mvvm.util.base.dialog.bottomsheet.BaseBottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopupDialog : BaseBottomSheetDialogFragment() {

    object Extra {
        const val TITLE: String = "$TAG.Extra.title"
        const val DESCRIPTION: String = "$TAG.Extra.description"
        const val POSITIVE: String = "$TAG.Extra.positive"
        const val NEGATIVE: String = "$TAG.Extra.negative"
    }

    companion object {
        const val TAG: String = "PopupDialog"
        const val TAG_NETWORK_ERROR: String = "PopupDialog(NetworkError)"

        fun newInstance(
            title: String? = null,
            description: String? = null,
            positive: String? = null,
            negative: String? = null
        ): PopupDialog = PopupDialog().apply {
            arguments = Bundle()
            arguments?.putString(Extra.TITLE, title)
            arguments?.putString(Extra.DESCRIPTION, description)
            arguments?.putString(Extra.POSITIVE, positive)
            arguments?.putString(Extra.NEGATIVE, negative)
        }

        fun createNetworkError(): PopupDialog = newInstance(
            title = App.context.getString(R.string.error),
            description = App.context.getString(R.string.network_error_message),
            positive = App.context.getString(R.string.confirm)
        )
    }

    private val viewModel: PopupViewModel by viewModel()

    private val binding: DialogPopupBinding by lazy {
        DialogPopupBinding.bind(view!!)
    }

    var onClickPositiveListener: (() -> Unit)? = null
    var onClickNegativeListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_popup, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBinding()
        initViewModel()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViewModel() {
        viewModel.startNegativeEvent.observe(this, Observer {
            startPositiveEvent()
        })
        viewModel.startPositiveEvent.observe(this, Observer {
            startNegativeEvent()
        })
    }

    private fun startPositiveEvent() {
        onClickPositiveListener?.let {
            it()
        }
        dismiss()
    }

    private fun startNegativeEvent() {
        onClickNegativeListener?.let {
            it()
        }
        dismiss()
    }

    override fun onSetupInstanceState(savedInstanceState: Bundle) {
        super.onSetupInstanceState(savedInstanceState)
        viewModel.setTitle(savedInstanceState.getString(Extra.TITLE))
        viewModel.setDescription(savedInstanceState.getString(Extra.DESCRIPTION))
        viewModel.setPositive(savedInstanceState.getString(Extra.POSITIVE))
        viewModel.setNegative(savedInstanceState.getString(Extra.NEGATIVE))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Extra.TITLE, viewModel.title.value)
        outState.putString(Extra.DESCRIPTION, viewModel.description.value)
        outState.putString(Extra.POSITIVE, viewModel.positive.value)
        outState.putString(Extra.NEGATIVE, viewModel.negative.value)
    }

}

