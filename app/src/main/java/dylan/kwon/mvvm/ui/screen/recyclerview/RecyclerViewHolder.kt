package dylan.kwon.mvvm.ui.screen.recyclerview

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.data.remote.dto.User
import dylan.kwon.mvvm.databinding.RecyclerviewItemBinding
import dylan.kwon.mvvm.util.base.adapter.recyclerview.holder.BaseRecyclerViewHolder
import dylan.kwon.mvvm.util.base.livedata.StateLiveData

class RecyclerViewHolder(

    parent: ViewGroup,
    lifecycleOwner: LifecycleOwner,
    viewModel: ViewModel

) : BaseRecyclerViewHolder(parent, R.layout.recyclerview_item) {

    private val _user: StateLiveData<User> = StateLiveData()
    val user: LiveData<User>
        get() = _user

    private val binding: RecyclerviewItemBinding by lazy {
        RecyclerviewItemBinding.bind(itemView)
    }

    init {
        binding.viewHolder = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
    }

    fun onBind(user: User) {
        _user.value = user
    }

    interface ViewModel {
        fun printUserInfo(user: User?)
    }

}