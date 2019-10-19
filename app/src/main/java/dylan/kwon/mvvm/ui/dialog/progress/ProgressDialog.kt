package dylan.kwon.mvvm.ui.dialog.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.databinding.DialogProgressBinding
import dylan.kwon.mvvm.util.base.dialog.popup.BaseDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgressDialog : BaseDialogFragment() {

    object Extra {
        const val DIM_COLOR: String = "$TAG.Extra.DIM_COLOR"
    }

    companion object {
        const val TAG: String = "ProgressDialog"

        fun newInstance(@ColorInt dimColor: Int = 0): ProgressDialog = ProgressDialog().apply {
            arguments = Bundle()
            arguments?.putInt(Extra.DIM_COLOR, dimColor)
        }
    }

    private val viewModel: ProgressViewModel by viewModel()

    private val binding: DialogProgressBinding by lazy {
        DialogProgressBinding.bind(view!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_progress, container, false)

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
        // ..
    }

    override fun onSetupInstanceState(savedInstanceState: Bundle) {
        super.onSetupInstanceState(savedInstanceState)
        viewModel.setDimColor(
            savedInstanceState.getInt(Extra.DIM_COLOR, 0)
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Extra.DIM_COLOR, viewModel.dimColor.value ?: 0)
    }

}
