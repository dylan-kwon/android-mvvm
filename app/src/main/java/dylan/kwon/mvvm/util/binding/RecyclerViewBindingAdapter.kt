package dylan.kwon.mvvm.util.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dylan.kwon.mvvm.util.base.adapter.recyclerview.BaseRecyclerViewAdapter

object RecyclerViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("items")
    @Suppress("UNCHECKED_CAST")
    fun bindItems(
        view: RecyclerView,
        items: MutableList<*>?
    ) {
        when (view.adapter) {
            is BaseRecyclerViewAdapter<*> -> {
                val adapter: BaseRecyclerViewAdapter<Any> = (view.adapter as? BaseRecyclerViewAdapter<Any>) ?: return
                adapter.items = items as? MutableList<Any> ?: mutableListOf()
            }
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "position",
            "isSmoothScroll"
        ],
        requireAll = false
    )
    fun bindScrollToPosition(
        view: RecyclerView,
        position: Int? = null,
        isSmoothScroll: Boolean? = null
    ) {
        if (position == null) {
            return
        }
        when (isSmoothScroll) {
            null, true -> view.smoothScrollToPosition(position)
            else -> view.scrollToPosition(position)
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "onScrolled",
            "onScrollStateChanged"
        ],
        requireAll = false
    )
    fun bindOnScrollListener(
        view: RecyclerView,
        onScrolled: OnScrollListener? = null,
        onScrollStateChanged: OnScrollStateChangedListener? = null
    ) {
        view.clearOnScrollListeners()
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager: LinearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
                val itemCount: Int = layoutManager.itemCount

                val firstPosition: Int = layoutManager.findFirstCompletelyVisibleItemPosition()
                val lastPosition: Int = layoutManager.findLastCompletelyVisibleItemPosition()

                onScrolled?.onScrolled(
                    itemCount = itemCount,
                    firstVisiblePosition = firstPosition,
                    lastVisiblePosition = lastPosition,
                    dx = dx,
                    dy = dy
                )
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                onScrollStateChanged?.onScrollStateChanged(newState)
            }
        })
    }

    interface OnScrollListener {
        fun onScrolled(itemCount: Int, firstVisiblePosition: Int, lastVisiblePosition: Int, dx: Int, dy: Int)
    }

    interface OnScrollStateChangedListener {
        fun onScrollStateChanged(newState: Int)
    }

}
