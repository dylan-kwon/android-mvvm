package dylan.kwon.mvvm.util.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import dylan.kwon.mvvm.util.base.adapter.recyclerview.BaseRecyclerViewAdapter
import dylan.kwon.mvvm.util.base.adapter.viewpager.BaseFragmentPagerAdapter
import dylan.kwon.mvvm.util.base.adapter.viewpager.BaseFragmentStatePagerAdapter
import dylan.kwon.mvvm.util.base.adapter.viewpager.BasePagerAdapter
import dylan.kwon.mvvm.util.base.view.BaseViewPager

object ViewPager2BindingAdapter {

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter("items")
    fun bindItems(view: ViewPager2, items: MutableList<*>? = null) {
        when (view.adapter) {
            is BaseRecyclerViewAdapter<*> -> {
                val adapter: BaseRecyclerViewAdapter<Any> = view.adapter as? BaseRecyclerViewAdapter<Any> ?: return
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

}
