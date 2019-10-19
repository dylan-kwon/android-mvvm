package dylan.kwon.mvvm.util.base.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.util.FontFactory

@SuppressLint("PrivateResource", "CustomViewStyleable")
open class BaseAutoCompleteTextView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.autoCompleteTextViewStyle

) : AppCompatAutoCompleteTextView(context, attrs, defStyleAttr) {

    init {
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
