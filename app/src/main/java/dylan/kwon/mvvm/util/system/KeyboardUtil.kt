package dylan.kwon.mvvm.util.system

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardUtil(applicationContext: Context) {

    private val instance: InputMethodManager =
        applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


    /**
     * 해당 뷰를 타겟으로 키보드를 띄움
     *
     * @param view 키보드로 입력받을 뷰
     */
    fun showKeyboard(view: View, delay: Long = 50) {
        view.postDelayed({
            view.requestFocus()
            instance.showSoftInput(view, 0)
        }, delay)
    }


    /**
     * 해당 뷰의 키보드를 닫음
     *
     * @param view 키보드 입력을 받는 뷰
     */
    fun hideKeyboard(view: View) {
        view.clearFocus()
        instance.hideSoftInputFromWindow(view.windowToken, 0)
    }


    /**
     * 액티비티에 띄워진 키보드를 닫음
     *
     * @param activity 키보드가 나와있는 액티비티
     */
    fun hideKeyboard(activity: Activity) {
        val iBinder: IBinder? = activity.currentFocus?.windowToken
        instance.hideSoftInputFromWindow(iBinder, 0)
    }

}
