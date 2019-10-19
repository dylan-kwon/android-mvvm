package dylan.kwon.mvvm.util.base.adapter.viewpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import dylan.kwon.mvvm.App

abstract class BaseFragmentPagerAdapter<T : Any>(

    private val fragmentManager: FragmentManager

) : FragmentPagerAdapter(fragmentManager) {

    protected val applicationContext: Context
        get() = App.context

    open var items: MutableList<T> = mutableListOf()
        set(value) {
            tempItems = field

            field.clear()
            field.addAll(value)

            notifyDataSetChanged()
        }

    private var tempItems: MutableList<T> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
        }

    override fun getCount(): Int = this.items.size

    final override fun getItemId(position: Int): Long =
        super.getItemId(position)

    fun createFragmentTag(viewPager: ViewPager, position: Int): String =
        createFragmentTag(viewPager.id, position)

    fun createFragmentTag(containerId: Int, position: Int): String =
        "android:switcher:$containerId:${getItemId(position)}"

    open fun findFragment(container: ViewPager, position: Int): Fragment? =
        findFragment(container.id, position)

    open fun findFragment(containerId: Int, position: Int): Fragment? {
        val tag: String = createFragmentTag(containerId, position)
        return fragmentManager.findFragmentByTag(tag)
    }

    override fun getItemPosition(`object`: Any): Int {
        val fragment: Fragment = (`object` as? Fragment) ?: return POSITION_NONE
        val position: Int = fragment.tag?.split(":")?.get(1)?.toInt() ?: return POSITION_NONE

        return try {
            val newItem: Any = items[position]
            val oldItem: Any = tempItems[position]

            when (oldItem == newItem) {
                true -> POSITION_UNCHANGED
                false -> POSITION_NONE
            }

        } catch (e: IndexOutOfBoundsException) {
            POSITION_NONE
        }
    }

}
