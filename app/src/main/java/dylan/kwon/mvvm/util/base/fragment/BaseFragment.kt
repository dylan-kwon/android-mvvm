package dylan.kwon.mvvm.util.base.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.util.LogUtil
import dylan.kwon.mvvm.util.system.KeyboardUtil
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    protected val applicationContext: Context
        get() = App.context

    protected lateinit var instanceState: Bundle

    protected val keyboardUtil: KeyboardUtil by inject()

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var isRestart: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtil.d(tag, "onAttach!!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(tag, "onCreate!!")
    }

    abstract override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.d(tag, "onViewCreated!!")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.d(tag, "onActivityCreated!!")

        instanceState = savedInstanceState ?: arguments ?: Bundle().also {
            arguments = it
        }
        onSetupInstanceState(instanceState)
    }

    @CallSuper
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtil.d(tag, "onHiddenChanged!!")
    }

    override fun onStart() {
        if (isRestart) {
            onRestart()
        }
        super.onStart()
        LogUtil.d(tag, "onStart!!")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d(tag, "onResume!!")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d(tag, "onPause!!")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d(tag, "onStop!!")
        if (!isRestart) {
            isRestart = true
        }
    }

    open fun onRestart() {
        LogUtil.d(tag, "onRestart!!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.d(tag, "onDestroyView!!")
        if (isRestart) {
            isRestart = false
        }
        activity?.let {
            keyboardUtil.hideKeyboard(it)
        }
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(tag, "onDestroy!!")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtil.d(tag, "onDetach!!")
    }

    open fun onSetupInstanceState(savedInstanceState: Bundle) {
        LogUtil.d(tag, "onSetupInstanceState!!")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.d(tag, "onSaveInstanceState!!")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtil.d(tag, "onActivityResult!!")
    }

    fun replace(fragmentManager: FragmentManager, container: View, tag: String) {
        if (!isAdded) {
            return
        }
        fragmentManager.beginTransaction()
            .replace(container.id, this, tag)
            .commit()
    }

    fun add(fragmentManager: FragmentManager, container: View, tag: String) {
        if (isAdded) {
            return
        }
        fragmentManager.beginTransaction()
            .add(container.id, this, tag)
            .commit()
    }

    fun remove() {
        if (!isAdded) {
            return
        }
        fragmentManager?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }

    fun attach(fragmentManager: FragmentManager, container: View, tag: String) {
        if (!isAdded) {
            add(fragmentManager, container, tag)
            return
        }
        if (!isDetached) {
            return
        }
        fragmentManager.beginTransaction()
            .attach(this)
            .commit()
    }

    fun detach() {
        if (!isAdded || isDetached) {
            return
        }
        fragmentManager?.beginTransaction()
            ?.detach(this)
            ?.commit()
    }

    fun show(fragmentManager: FragmentManager, container: View, tag: String) {
        if (!isAdded) {
            add(fragmentManager, container, tag)
            return
        }
        if (isVisible) {
            return
        }
        fragmentManager.beginTransaction()
            .show(this)
            .commit()
    }

    fun hide() {
        if (!isAdded || !isVisible) {
            return
        }
        fragmentManager?.beginTransaction()
            ?.hide(this)
            ?.commit()
    }

    fun finish() {
        activity?.finish()
    }

}
