package dylan.kwon.mvvm.util.base.adapter.viewpager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.util.base.adapter.viewpager.holder.BaseViewPagerHolder

abstract class BasePagerAdapter<T : Any> : PagerAdapter() {

    protected val applicationContext: Context
        get() = App.context

    open var items: MutableList<T> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun getCount(): Int = this.items.size

    abstract override fun instantiateItem(container: ViewGroup, position: Int): Any

    override fun getItemPosition(`object`: Any): Int {
        val view: View = (`object` as? View) ?: return POSITION_NONE
        val holder: BaseViewPagerHolder = (view.tag as? BaseViewPagerHolder) ?: return POSITION_NONE

        return try {
            val oldItem: Any? = holder.getItem()
            val newItem: Any = this.items[holder.position]

            when (oldItem == newItem) {
                true -> POSITION_UNCHANGED
                false -> POSITION_NONE
            }

        } catch (e: IndexOutOfBoundsException) {
            POSITION_NONE
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (`object` !is View) {
            return
        }
        if (`object`.tag !is BaseViewPagerHolder) {
            return
        }
        container.removeView(`object`)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

}
