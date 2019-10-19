package dylan.kwon.mvvm.util.base.dialog.bottomsheet

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.util.LogUtil
import dylan.kwon.mvvm.util.system.ActivityUtil
import dylan.kwon.mvvm.util.system.KeyboardUtil
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    object Extra {
        const val STATE: String = "BaseBottomSheetDialogFragment.Extra.STATE"
        const val IS_HIDEABLE: String = "BaseBottomSheetDialogFragment.Extra.IS_HIDEABLE"
        const val PEEK_HEIGHT: String = "BaseBottomSheetDialogFragment.Extra.PEEK_HEIGHT"
        const val DIM_AMOUNT: String = "BaseBottomSheetDialogFragment.Extra.DIM_AMOUNT"
    }

    protected val applicationContext: Context
        get() = App.context

    protected lateinit var instanceState: Bundle

    protected val rootView: ViewGroup
        get() = view?.parent as ViewGroup

    protected val bottomSheetBehavior: BottomSheetBehavior<*> by lazy {
        val lp: CoordinatorLayout.LayoutParams =
            rootView.layoutParams as CoordinatorLayout.LayoutParams

        return@lazy lp.behavior as BottomSheetBehavior<*>
    }

    protected var windowAttributes: WindowManager.LayoutParams?
        set(value) {
            dialog?.window?.attributes = value
        }
        get() = dialog?.window?.attributes

    var state: Int
        set(value) {
            bottomSheetBehavior.state = value
        }
        get() = bottomSheetBehavior.state

    var peekHeight: Int
        set(value) {
            bottomSheetBehavior.peekHeight = value
        }
        get() = bottomSheetBehavior.peekHeight

    var isHideable: Boolean
        set(value) {
            bottomSheetBehavior.isHideable = value
        }
        get() = bottomSheetBehavior.isHideable

    var dimAmount: Float
        set(value) {
            windowAttributes?.dimAmount = value
        }
        get() = windowAttributes?.dimAmount ?: 0.0f

    var onSlideListener: ((bottomSheet: View, slideOffset: Float) -> Unit)? = null

    var onStateChangedListener: ((bottomSheet: View, newState: Int) -> Unit)? = null

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
        return object : BaseBottomSheetDialog(context!!, theme) {
            override fun onBackPressed() {
                if (!this@BaseBottomSheetDialogFragment.onBackPressed()) {
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

        rootView.background = null

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                onSlideListener?.let {
                    it(bottomSheet, slideOffset)
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                    BottomSheetBehavior.STATE_DRAGGING -> Unit
                    BottomSheetBehavior.STATE_EXPANDED -> Unit
                    BottomSheetBehavior.STATE_SETTLING -> Unit
                    BottomSheetBehavior.STATE_COLLAPSED -> Unit
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> Unit
                }
                onStateChangedListener?.let {
                    it(bottomSheet, newState)
                }
            }
        })
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
        compositeDisposable.clear()
        bottomSheetBehavior.setBottomSheetCallback(null)
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
        isHideable = savedInstanceState.getBoolean(Extra.IS_HIDEABLE, true)
        state = savedInstanceState.getInt(Extra.STATE, BottomSheetBehavior.STATE_COLLAPSED)
        peekHeight = savedInstanceState.getInt(Extra.PEEK_HEIGHT, BottomSheetBehavior.PEEK_HEIGHT_AUTO)
        dimAmount = savedInstanceState.getFloat(Extra.DIM_AMOUNT, 0.6f)
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.d(tag, "onSaveInstanceState!!")
        outState.putInt(Extra.STATE, state)
        outState.putInt(Extra.PEEK_HEIGHT, peekHeight)
        outState.putBoolean(Extra.IS_HIDEABLE, isHideable)
        outState.putFloat(Extra.DIM_AMOUNT, dimAmount)
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
