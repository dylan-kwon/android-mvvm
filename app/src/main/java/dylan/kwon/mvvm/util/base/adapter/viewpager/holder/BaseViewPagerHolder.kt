package dylan.kwon.mvvm.util.base.adapter.viewpager.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.util.base.adapter.ViewHolder
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewPagerHolder(

    protected val itemView: View

) : ViewHolder {

    companion object {
        const val NO_POSITION = -1
    }

    override val applicationContext: Context
        get() = App.context

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var position: Int = NO_POSITION
        private set

    constructor(container: ViewGroup, layoutRes: Int) : this(
        LayoutInflater.from(container.context).inflate(layoutRes, container, false)
    )

    fun onBind(position: Int) {
        this.position = position
    }

    abstract fun getItem(): Any?

}
