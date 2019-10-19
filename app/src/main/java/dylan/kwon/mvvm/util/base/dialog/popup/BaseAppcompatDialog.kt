package dylan.kwon.mvvm.util.base.dialog.popup

import android.content.Context
import android.view.Window
import androidx.appcompat.app.AppCompatDialog

open class BaseAppcompatDialog @JvmOverloads constructor(

    context: Context,
    theme: Int = 0

) : AppCompatDialog(context, theme) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

}
