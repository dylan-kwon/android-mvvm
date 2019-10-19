package dylan.kwon.mvvm.ui.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.databinding.FragmentChildBinding
import dylan.kwon.mvvm.util.LogUtil
import dylan.kwon.mvvm.util.base.fragment.BaseFragment

class ChildFragment : BaseFragment(), Interaction {

    companion object {
        const val TAG: String = "ChildFragment"

        var lock: Boolean = false

        fun newInstance(): ChildFragment = ChildFragment().apply {
            arguments = Bundle()
        }
    }

    private val binding: FragmentChildBinding by lazy {
        FragmentChildBinding.bind(view!!)
    }

    private val interaction: Interaction by lazy {
        parentFragment as? Interaction
            ?: context as? Interaction
            ?: throw ClassCastException()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_child, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        interaction.onInitializedChildFragment()
        showFragment(savedInstanceState)
    }

    private fun showFragment(savedInstanceState: Bundle?) {
        if (lock) {
            return
        }
        if (savedInstanceState != null) {
            return
        }
        newInstance()
            .add(childFragmentManager, binding.container, "$TAG(child)")

        lock = true
    }

    override fun onInitializedChildFragment() {
        LogUtil.d(tag, "onInitializedChildFragment")
    }

}

interface Interaction {
    fun onInitializedChildFragment()
}