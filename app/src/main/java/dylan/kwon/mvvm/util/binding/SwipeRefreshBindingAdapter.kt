package dylan.kwon.mvvm.util.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

object SwipeRefreshBindingAdapter {

    @JvmStatic
    @BindingAdapter("isRefreshing")
    fun bindRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean? = null) {
        if (view.isRefreshing == isRefreshing) {
            return
        }
        view.isRefreshing = isRefreshing ?: false
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "isRefreshing", event = "changedIsRefreshing")
    fun inverseBindRefreshing(view: SwipeRefreshLayout): Boolean {
        return view.isRefreshing
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "onRefresh",
            "changedIsRefreshing"
        ], requireAll = false
    )
    fun bindRefreshingListener(
        view: SwipeRefreshLayout,
        listener: SwipeRefreshLayout.OnRefreshListener?,
        inverseBindingListener: InverseBindingListener?
    ) {
        view.setOnRefreshListener {
            inverseBindingListener?.onChange()
            listener?.onRefresh()
        }
    }

}