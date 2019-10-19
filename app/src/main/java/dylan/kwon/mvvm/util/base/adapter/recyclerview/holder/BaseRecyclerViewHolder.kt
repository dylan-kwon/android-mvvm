package dylan.kwon.mvvm.util.base.adapter.recyclerview.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.util.base.adapter.ViewHolder
import io.reactivex.disposables.CompositeDisposable

abstract class BaseRecyclerViewHolder(

    itemView: View

) : RecyclerView.ViewHolder(itemView), ViewHolder {

    override val applicationContext: Context
        get() = App.context

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    constructor(parent: ViewGroup, layoutRes: Int) : this(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    )

}
