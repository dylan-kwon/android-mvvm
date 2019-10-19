package dylan.kwon.mvvm.util.extension

import androidx.recyclerview.widget.RecyclerView
import dylan.kwon.mvvm.util.base.adapter.recyclerview.BaseRecyclerViewAdapter

infix fun RecyclerView.refreshViewHolder(adapterPosition: Int) {
    val adapter: BaseRecyclerViewAdapter<*> =
        this.adapter as BaseRecyclerViewAdapter<*>? ?: return

    adapter.notifyItemChanged(adapterPosition)
}
