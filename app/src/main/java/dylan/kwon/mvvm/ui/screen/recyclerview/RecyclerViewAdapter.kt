package dylan.kwon.mvvm.ui.screen.recyclerview

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import dylan.kwon.mvvm.data.remote.dto.User
import dylan.kwon.mvvm.util.base.adapter.recyclerview.BaseRecyclerViewAdapter
import dylan.kwon.mvvm.util.base.adapter.recyclerview.holder.BaseRecyclerViewHolder

class RecyclerViewAdapter(

    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: RecyclerViewHolder.ViewModel

) : BaseRecyclerViewAdapter<User>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder =
        RecyclerViewHolder(parent, lifecycleOwner, viewModel)

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        val adapterPosition: Int = holder.adapterPosition.takeIf {
            it != RecyclerView.NO_POSITION
        } ?: return

        val user: User = this.items[adapterPosition]

        when (holder) {
            is RecyclerViewHolder -> holder.onBind(user)
        }
    }

}