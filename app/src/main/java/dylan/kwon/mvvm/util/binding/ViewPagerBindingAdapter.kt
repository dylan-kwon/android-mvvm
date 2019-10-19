package dylan.kwon.mvvm.util.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.viewpager.widget.ViewPager
import dylan.kwon.mvvm.util.base.adapter.viewpager.BaseFragmentPagerAdapter
import dylan.kwon.mvvm.util.base.adapter.viewpager.BaseFragmentStatePagerAdapter
import dylan.kwon.mvvm.util.base.adapter.viewpager.BasePagerAdapter
import dylan.kwon.mvvm.util.base.view.BaseViewPager

object ViewPagerBindingAdapter {

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter("items")
    fun bindItems(view: ViewPager, items: MutableList<*>? = null) {
        when (view.adapter) {
            is BasePagerAdapter<*> -> {
                val adapter: BasePagerAdapter<Any> = view.adapter as? BasePagerAdapter<Any> ?: return
                if (adapter.items != items) {
                    adapter.items = items as? MutableList<Any> ?: mutableListOf()
                }
            }

            is BaseFragmentPagerAdapter<*> -> {
                val adapter: BaseFragmentPagerAdapter<Any> = view.adapter as? BaseFragmentPagerAdapter<Any> ?: return
                if (adapter.items != items) {
                    adapter.items = items as? ArrayList<Any> ?: arrayListOf()
                }
            }

            is BaseFragmentStatePagerAdapter<*> -> {
                val adapter: BaseFragmentStatePagerAdapter<Any> = view.adapter as? BaseFragmentStatePagerAdapter<Any> ?: return
                if (adapter.items != items) {
                    adapter.items = items as? ArrayList<Any> ?: arrayListOf()
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("pageMargin")
    fun bindViewPagerMargin(view: ViewPager, size: Int? = null) {
        if (view.pageMargin == size) {
            return
        }
        view.pageMargin = size ?: 0
    }

    @JvmStatic
    @BindingAdapter("swipeEnabled")
    fun bindSwipeEnabled(view: BaseViewPager, isSwipeEnabled: Boolean? = null) {
        view.isSwipeEnabled = isSwipeEnabled ?: false
    }

    @JvmStatic
    @BindingAdapter("offscreenPageLimit")
    fun bindOffscreenPageLimit(view: ViewPager, limit: Int? = null) {
        if (view.offscreenPageLimit == limit) {
            return
        }
        view.offscreenPageLimit = limit ?: 1
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "currentPosition",
            "isSmoothScroll"
        ],
        requireAll = false
    )
    fun bindCurrentPosition(
        view: ViewPager,
        currentPosition: Int? = null,
        isSmoothScroll: Boolean? = null
    ) {
        if (currentPosition == null) {
            return
        }
        if (view.currentItem == currentPosition) {
            return
        }
        view.setCurrentItem(currentPosition, isSmoothScroll ?: true)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "currentPosition", event = "changedCurrentPosition")
    fun inverseBindCurrentPosition(view: ViewPager): Int {
        return view.currentItem
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "onPageScrolled",
            "onPageSelected",
            "onPageScrollStateChanged",
            "changedCurrentPosition"
        ],
        requireAll = false
    )
    fun bindOnPageChangeListener(
        view: ViewPager,
        onPageScrolled: OnViewPagerScrolledListener?,
        onPageSelected: OnViewPagerSelectedListener?,
        onPageScrollStateChanged: OnViewPagerScrollStateChanged?,
        onPositionChanged: InverseBindingListener?
    ) {
        view.clearOnPageChangeListeners()
        view.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                onPageScrolled?.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                onPositionChanged?.onChange()
                onPageSelected?.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                onPageScrollStateChanged?.onPageScrollStateChanged(state)
            }
        })
    }

    interface OnViewPagerScrolledListener {
        fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    }

    interface OnViewPagerSelectedListener {
        fun onPageSelected(position: Int)
    }

    interface OnViewPagerScrollStateChanged {
        fun onPageScrollStateChanged(state: Int)
    }

}
