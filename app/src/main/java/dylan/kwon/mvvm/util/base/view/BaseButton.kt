package dylan.kwon.mvvm.util.base.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.util.FontFactory

@SuppressLint("PrivateResource", "CustomViewStyleable")
open class BaseButton @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.buttonStyle

) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stateListAnimator = null
        }
        minWidth = 0
        minHeight = 0
        minimumWidth = 0
        minimumHeight = 0
        includeFontPadding = false

        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AppCompatTextView, defStyleAttr, 0)

        val font: String? =
            typedArray.getString(R.styleable.AppCompatTextView_fontFamily)

        if (!isInEditMode && font.isNullOrEmpty()) {
            super.setTypeface(FontFactory.getDefaultFont(), typeface.style)
        }

        typedArray.recycle()
    }

}
