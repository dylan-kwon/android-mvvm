package dylan.kwon.mvvm.util.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.tedpark.tedpermission.rx2.TedRx2Permission
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.ui.dialog.popup.PopupDialog
import dylan.kwon.mvvm.ui.dialog.progress.ProgressDialog
import dylan.kwon.mvvm.util.base.dialog.bottomsheet.BaseBottomSheetDialogFragment
import dylan.kwon.mvvm.util.base.dialog.popup.BaseDialogFragment
import dylan.kwon.mvvm.util.base.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Context를 리턴.
 */
fun LifecycleOwner.getContext(): Context = when (this) {
    is Activity -> this
    is Fragment -> this.context ?: throw NullPointerException("The context of the fragment is null.")
    else -> throw NullPointerException("This method can only use Activity or Fragment.")
}

/**
 * Activity, Fragment의 RootView를 리턴.
 */
fun LifecycleOwner.getRootView(): View = when (this) {
    is Activity -> (this.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
    is Fragment -> this.view?.rootView ?: throw NullPointerException("The view of the fragment is null.")
    else -> throw NullPointerException("This method can only use Activity or Fragment.")
}

/**
 * FragmentManager를 리턴.
 */
fun LifecycleOwner.getFragmentManager(): FragmentManager = when (this) {
    is AppCompatActivity -> this.supportFragmentManager
    is Fragment -> this.childFragmentManager
    else -> throw NullPointerException("This method can only use Activity or Fragment.")
}

/**
 *  BaseViewModel의 기본 Event 구독.
 */
fun LifecycleOwner.observeBaseViewModelEvent(
    viewModel: BaseViewModel,
    isShowToast: Boolean = true,
    isSnackbar: Boolean = true,
    isShowProgress: Boolean = true,
    isDismissProgress: Boolean = true,
    isShowNetworkError: Boolean = true
) {
    if (isShowToast) {
        viewModel.showToast.observe(this, Observer {
            it?.let { message ->
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    if (isSnackbar) {
        viewModel.showSnackbar.observe(this, Observer {
            it?.let { message ->
                Snackbar.make(getRootView(), message, Snackbar.LENGTH_SHORT).show()
            }
        })
    }
    if (isShowProgress) {
        viewModel.showProgress.observe(this, Observer {
            showProgressDialog(ContextCompat.getColor(getContext(), R.color.dialogBackground))
        })
    }
    if (isDismissProgress) {
        viewModel.dismissProgress.observe(this, Observer {
            dismissDialog(ProgressDialog.TAG)
        })
    }
    if (isShowNetworkError) {
        viewModel.showNetworkError.observe(this, Observer {
            showNetworkErrorDialog()
        })
    }
}

/**
 * ProgressDialog 띄우기.
 */
fun LifecycleOwner.showProgressDialog(dimAmount: Int = 0) = ProgressDialog.newInstance(dimAmount)
    .show(getFragmentManager(), ProgressDialog.TAG)

/**
 * networkErrorDialog 띄우기.
 */
fun LifecycleOwner.showNetworkErrorDialog(
    onDismissListener: (() -> Unit)? = null
) = PopupDialog.createNetworkError().also {
    it.onDismissListener = onDismissListener
}.show(getFragmentManager(), PopupDialog.TAG_NETWORK_ERROR)

/**
 * DialogFragment 닫기.
 */
fun LifecycleOwner.dismissDialog(dialogTag: String) {
    getFragmentManager().findFragmentByTag(dialogTag).let { dialog ->
        when (dialog) {
            is BaseDialogFragment -> dialog.dismiss()
            is BaseBottomSheetDialogFragment -> dialog.dismiss()
        }
    }
}

/**
 * DialogFragment 전체 닫기.
 */
fun LifecycleOwner.dismissAllDialogs() {
    for (fragment: Fragment in getFragmentManager().fragments) {
        dismissDialog(fragment.tag ?: continue)
    }
}

/**
 * 퍼미션 체크.
 */
fun LifecycleOwner.checkPermission(
    permissions: Array<String>,
    onGranted: (() -> Unit)?,
    onDenied: (() -> Unit)? = null
): Disposable = TedRx2Permission.with(getContext())

    // 퍼미션이 필요한 이유 설명
    // .setRationaleMessage(context.getString(R.string.permission_rational_message))
    // .setRationaleConfirmText(context.getString(R.string.common_ok))

    // 퍼미션 요청을 거절한 경우 보이는 메세지
    .setDeniedMessage(App.context.getString(R.string.permission_denied_message))
    .setDeniedCloseButtonText(App.context.getString(R.string.close))

    // 퍼미션을 거절한 경우 설정 화면으로 바로 이동 버튼
    .setGotoSettingButton(true)
    .setGotoSettingButtonText(App.context.getString(R.string.setting))

    // 권한을 획득할 퍼미션
    .setPermissions(*permissions)

    .request()

    // Thread 설정
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

    // 구독
    .subscribe({ tedPermissionResult ->
        when (tedPermissionResult.isGranted) {
            true -> onGranted?.let {
                it()
            }
            false -> onDenied?.let {
                it()

            } ?: let {
                Toast.makeText(App.context, R.string.permission_denied_message, Toast.LENGTH_SHORT).show()
            }
        }
    }, {
        it.printStackTrace()
    })
