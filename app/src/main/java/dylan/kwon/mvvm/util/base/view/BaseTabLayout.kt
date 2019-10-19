package dylan.kwon.mvvm.util.base.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Px
import com.google.android.material.tabs.TabLayout
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.util.FontFactory

open class BaseTabLayout @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : TabLayout(context, attrs, defStyleAttr) {

    private var tabMargin: Int = 0

    val tabs: MutableList<View> by lazy {
        mutableListOf<View>()
    }

    val slidingTabStrip: ViewGroup by lazy {
        getChildAt(0) as ViewGroup
    }

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.BaseTabLayout, defStyleAttr, 0
        )

        this.tabMargin = typedArray.getDimensionPixelSize(
            R.styleable.BaseTabLayout_tabMargin, 0
        )

        typedArray.recycle()
    }

    override fun getTabCount(): Int {
        return tabs.size
    }

    override fun addTab(tab: Tab, setSelected: Boolean) {
        super.addTab(tab, setSelected)

        val tabView: ViewGroup =
            slidingTabStrip.getChildAt(tab.position) as ViewGroup

        tabs.add(tab.position, tabView)
        setTabMargin(tab.position, tabMargin)

        if (!isInEditMode) {
            val titleView: TextView? = findTabTitleView(tab.position)
            titleView?.setTypeface(FontFactory.getDefaultFont(), titleView.typeface.style)
        }
    }

    fun findTabTitleView(position: Int): TextView? {
        val tabView: ViewGroup = tabs[position] as ViewGroup
        for (i: Int in 0 until tabView.childCount) {
            val childView: View = tabView.getChildAt(i)

            if (childView is TextView) {
                return childView
            }
        }
        return null
    }

    override fun removeTab(tab: Tab?) {
        val position: Int = tab?.position ?: return
        val tabView: View = slidingTabStrip.getChildAt(position)
        tabs.remove(tabView)
        super.removeTab(tab)
    }

    fun setTabMargin(@Px px: Int) {
        if (tabMargin == px) {
            return
        }
        for (position in 0 until tabs.size) {
            setTabMargin(position, px)
        }
    }

    fun setTabMargin(position: Int, @Px px: Int) {
        val tab: View = tabs[position]
        val lp: MarginLayoutParams = tab.layoutParams as? MarginLayoutParams ?: return

        lp.rightMargin = px
        tab.layoutParams = lp

        tabMargin = px
    }


}
