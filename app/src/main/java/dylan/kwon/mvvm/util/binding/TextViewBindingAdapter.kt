package dylan.kwon.mvvm.util.binding

import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.KeyEvent
import android.widget.TextView
import androidx.databinding.BindingAdapter


object TextViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("html")
    fun bindHtml(view: TextView, html: String?) {
        if (view.text == html) {
            return
        }
        if (html == null) {
            view.text = ""
            return
        }
        val imageGetter: Html.ImageGetter? = view as? Html.ImageGetter

        view.text = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Html.fromHtml(html, imageGetter, null)
        } else {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, imageGetter, null)
        }
        view.movementMethod = if (html.contains("href")) {
            LinkMovementMethod.getInstance()
        } else {
            null
        }
    }

    @JvmStatic
    @BindingAdapter("textStyle")
    fun bindTextStyle(view: TextView, style: Int?) {
        if (view.typeface.style == style) {
            return
        }
        view.typeface = Typeface.create(view.typeface, style ?: Typeface.NORMAL)
    }

    @JvmStatic
    @BindingAdapter("onEditorAction")
    fun bindOnEditorActionListener(view: TextView, listener: OnEditorActionListener?) {
        view.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener listener?.onEditorAction(v, actionId, event) ?: false
        }
    }

    interface OnEditorActionListener {
        fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean
    }

}
