package dylan.kwon.mvvm.util.binding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import org.koin.core.KoinComponent

object ViewBindingAdapter : KoinComponent {

    @JvmStatic
    @BindingAdapter("layout_width")
    fun bindWidth(view: View, px: Int?) {
        if (view.layoutParams.width == px) {
            return
        }
        view.layoutParams.width = px ?: 0
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("layout_height")
    fun bindHeight(view: View, px: Int?) {
        if (view.layoutParams.height == px) {
            return
        }
        view.layoutParams.height = px ?: 0
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("layout_margin")
    fun bindMargin(view: View, px: Int?) {
        val tempPx: Int = px ?: 0

        val layoutParams: ViewGroup.MarginLayoutParams =
            (view.layoutParams as? ViewGroup.MarginLayoutParams) ?: return

        layoutParams.setMargins(tempPx, tempPx, tempPx, tempPx)
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("layout_marginTop")
    fun bindMargiTop(view: View, px: Int?) {
        val layoutParams: ViewGroup.MarginLayoutParams =
            (view.layoutParams as? ViewGroup.MarginLayoutParams) ?: return

        if (layoutParams.topMargin == px) {
            return
        }
        layoutParams.topMargin = px ?: 0
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("layout_marginBottom")
    fun bindMarginBottom(view: View, px: Int?) {
        val layoutParams: ViewGroup.MarginLayoutParams =
            (view.layoutParams as? ViewGroup.MarginLayoutParams) ?: return

        if (layoutParams.bottomMargin == px) {
            return
        }
        layoutParams.bottomMargin = px ?: 0
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("layout_marginStart")
    fun bindMarginLeft(view: View, px: Int?) {
        val layoutParams: ViewGroup.MarginLayoutParams =
            (view.layoutParams as? ViewGroup.MarginLayoutParams) ?: return

        if (layoutParams.marginStart == px) {
            return
        }
        layoutParams.marginStart = px ?: 0
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("layout_marginEnd")
    fun bindMarginRight(view: View, px: Int?) {
        val layoutParams: ViewGroup.MarginLayoutParams =
            (view.layoutParams as? ViewGroup.MarginLayoutParams) ?: return

        if (layoutParams.marginEnd == px) {
            return
        }
        layoutParams.marginEnd = px ?: 0
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("isVisibility")
    fun bindVisibility(view: View, visibility: Boolean?) {
        val newState: Int = when (visibility) {
            true -> View.VISIBLE
            else -> View.GONE
        }
        if (view.visibility == newState) {
            return
        }
        view.visibility = newState
    }

    @JvmStatic
    @BindingAdapter("enabled")
    fun bindEnabled(view: View, enabled: Boolean?) {
        if (view.isEnabled == enabled) {
            return
        }
        view.isEnabled = enabled ?: false
    }

}
