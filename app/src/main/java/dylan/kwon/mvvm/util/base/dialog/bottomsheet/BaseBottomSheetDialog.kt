package dylan.kwon.mvvm.util.base.dialog.bottomsheet

import android.content.Context
import android.view.Window
import com.google.android.material.bottomsheet.BottomSheetDialog

open class BaseBottomSheetDialog @JvmOverloads constructor(

    context: Context,
    theme: Int = 0

) : BottomSheetDialog(context, theme) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

}
