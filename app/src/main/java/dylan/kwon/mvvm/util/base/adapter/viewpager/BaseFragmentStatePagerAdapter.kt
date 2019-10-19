package dylan.kwon.mvvm.util.base.adapter.viewpager

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import dylan.kwon.mvvm.App

abstract class BaseFragmentStatePagerAdapter<T>(

    fragmentManager: FragmentManager

) : FragmentStatePagerAdapter(fragmentManager) {

    protected val applicationContext: Context
        get() = App.context

    open var items: MutableList<T> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun getCount(): Int = this.items.size

}
