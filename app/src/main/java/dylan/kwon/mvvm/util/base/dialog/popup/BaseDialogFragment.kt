package dylan.kwon.mvvm.util.base.dialog.popup

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.util.LogUtil
import dylan.kwon.mvvm.util.system.ActivityUtil
import dylan.kwon.mvvm.util.system.KeyboardUtil
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseDialogFragment : AppCompatDialogFragment() {

    protected val applicationContext: Context
        get() = App.context

    protected lateinit var instanceState: Bundle

    protected val window: Window?
        get() = dialog?.window

    protected var layoutParams: WindowManager.LayoutParams?
        get() {
            return window?.attributes
        }
        set(value) {
            window?.attributes = value
        }

    var onDismissListener: (() -> Unit)? = null

    protected val activityUtil: ActivityUtil by inject()

    protected val keyboardUtil: KeyboardUtil by inject()

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtil.d(tag, "onAttach!!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(tag, "onCreate!!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        LogUtil.d(tag, "onCreateDialog!!")
        return object : BaseAppcompatDialog(context!!, theme) {
            override fun onBackPressed() {
                if (!this@BaseDialogFragment.onBackPressed()) {
                    return
                }
                super.onBackPressed()
            }
        }
    }

    abstract override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.d(tag, "onViewCreated!!")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.d(tag, "onActivityCreated!!")

        layoutParams = layoutParams?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.0f
        }
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        instanceState = savedInstanceState ?: arguments ?: Bundle().also {
            arguments = it
        }
        onSetupInstanceState(instanceState)
    }

    override fun onStart() {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.d(tag, "onDestroyView!!")
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

    @CallSuper
    open fun onSetupInstanceState(savedInstanceState: Bundle) {
        LogUtil.d(tag, "onSetupInstanceState!!")
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.d(tag, "onSaveInstanceState!!")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtil.d(tag, "onActivityResult!!")
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!activityUtil.isForeground) {
            return
        }
        val dialog: Fragment? = manager.findFragmentByTag(tag)
        if (dialog != null && dialog.isAdded) {
            return
        }
        super.show(manager, tag)
    }

    fun showAllowingStateLoss(manager: FragmentManager?, tag: String?) {
        if (!activityUtil.isForeground) {
            return
        }
        val dialog: Fragment? = manager?.findFragmentByTag(tag)
        if (dialog != null && dialog.isAdded) {
            return
        }
        manager?.beginTransaction()
            ?.add(this, tag)
            ?.commitAllowingStateLoss()
    }

    override fun dismiss() {
        if (!activityUtil.isForeground) {
            return
        }
        if (!isAdded) {
            return
        }
        super.dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (!activityUtil.isForeground) {
            return
        }
        if (!isAdded) {
            return
        }
        onDismissListener?.let {
            it()
        }
        super.onDismiss(dialog)
    }

    open fun onBackPressed(): Boolean {
        // empty method.
        return true
    }

}
