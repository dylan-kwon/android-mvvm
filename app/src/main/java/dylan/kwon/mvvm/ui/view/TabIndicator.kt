package dylan.kwon.mvvm.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.database.DataSetObserver
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.viewpager.widget.ViewPager
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.util.LogUtil
import dylan.kwon.mvvm.util.base.adapter.viewpager.LoopPagerAdapter

open class TabIndicator @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {

        @JvmField
        val TAG: String = this::class.java.simpleName

        const val NO_POSITION: Int = -1
    }

    var position: Int = NO_POSITION

    private val indicatorViews: ArrayList<ImageView> = arrayListOf()

    @Px
    var indicatorWidth: Int = 0

    @Px
    var indicatorHeight: Int = 0

    @Px
    var indicatorMargin: Int = 0

    @DrawableRes
    var indicatorDrawableRes: Int = 0

    init {
        gravity = Gravity.CENTER

        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.TabIndicator, defStyleAttr, 0
        )
        val defaultSize: Int = (resources.getDimensionPixelSize(R.dimen._1dp) * 7)
        val defaultMargin: Int = (resources.getDimensionPixelSize(R.dimen._1dp) * 8.5).toInt()

        indicatorWidth = typedArray.getDimensionPixelSize(R.styleable.TabIndicator_indicator_width, defaultSize)
        indicatorHeight = typedArray.getDimensionPixelSize(R.styleable.TabIndicator_indicator_height, defaultSize)
        indicatorMargin = typedArray.getDimensionPixelSize(R.styleable.TabIndicator_indicator_margin, defaultMargin)
        indicatorDrawableRes = typedArray.getResourceId(R.styleable.TabIndicator_indicator_drawable, 0)

        typedArray.recycle()
    }

    infix fun setupWithViewPager(viewPager: ViewPager) {
        viewPager.adapter?.registerDataSetObserver(object : DataSetObserver() {
            override fun onChanged() {
                changeViewPageData(viewPager)
            }
        })
    }

    private fun changeViewPageData(viewPager: ViewPager) {
        val itemCount: Int = when (viewPager.adapter) {
            is LoopPagerAdapter<*> ->
                (viewPager.adapter as LoopPagerAdapter<*>).getItemCount()

            else -> viewPager.adapter?.count ?: 0
        }
        updateTabIndicator(itemCount) {
            invalidate()
            checkedTabPosition(viewPager.currentItem % itemCount)
        }
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (itemCount == 0) {
                    return
                }
                checkedTabPosition(position % itemCount)
            }
        })
    }

    private fun updateTabIndicator(itemCount: Int, onUpdated: () -> Unit) {
        removeAllIndicator(false)
        for (i: Int in 0 until itemCount) {
            ImageView(context).apply {
                setImageResource(indicatorDrawableRes)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                addView(this)

                viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        viewTreeObserver.removeOnPreDrawListener(this)

                        layoutParams.width = indicatorWidth
                        layoutParams.height = indicatorHeight

                        if (i < itemCount - 1) {
                            when (orientation) {
                                HORIZONTAL ->
                                    (layoutParams as MarginLayoutParams).rightMargin = indicatorMargin

                                VERTICAL ->
                                    (layoutParams as MarginLayoutParams).bottomMargin = indicatorMargin
                            }

                        }
                        layoutParams = layoutParams
                        indicatorViews.add(this@apply)

                        if (i == itemCount - 1) {
                            onUpdated()
                        }
                        return false
                    }
                })
            }

        }
    }

    private fun removeAllIndicator(isInvalidate: Boolean) {
        removeAllViews()
        if (isInvalidate) {
            invalidate()
        }
        indicatorViews.clear()
    }

    fun checkedTabPosition(position: Int) {
        try {
            if (this.position == position || this.position >= indicatorViews.size) {
                return
            }
            if (this.position != NO_POSITION) {
                val oldSelectIndicator: ImageView = indicatorViews[this.position]
                oldSelectIndicator.isSelected = false
            }
            val newSelectIndicator: ImageView = indicatorViews[position]
            newSelectIndicator.isSelected = true

            this.position = position

        } catch (e: IndexOutOfBoundsException) {
            LogUtil.w(TAG, "pass..")
        }
    }

}
