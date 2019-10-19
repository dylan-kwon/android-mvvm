package dylan.kwon.mvvm.util.base.adapter.recyclerview

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.util.base.adapter.recyclerview.holder.BaseRecyclerViewHolder
import dylan.kwon.mvvm.util.base.model.Model

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    protected val applicationContext: Context
        get() = App.context

    open var items: MutableList<T> = mutableListOf()
        set(value) {
            val diffCallback: DiffCallback<T> = DiffCallback(field, value)
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)

            field.clear()
            field.addAll(value)

            diffResult.dispatchUpdatesTo(this)
        }

    init {
        setHasStableIds(true)
    }

    override fun getItemCount(): Int = this.items.size

    final override fun setHasStableIds(hasStableIds: Boolean) =
        super.setHasStableIds(hasStableIds)

    override fun getItemId(position: Int): Long = this.items[position].let { item ->
        return when (hasStableIds()) {
            true -> return when (item) {
                is Model -> item.id ?: Model.NONE_ID
                else -> position.toLong()
            }
            else -> super.getItemId(position)
        }
    }

}
