package dylan.kwon.mvvm.util.base.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import dylan.kwon.mvvm.R
import org.koin.core.KoinComponent
import java.lang.reflect.Field

class BaseViewPager @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null

) : ViewPager(context, attrs), KoinComponent {

    object Orientation {
        const val VERTICAL: Int = 1
        const val HORIZONTAL: Int = 2
    }

    var isSwipeEnabled: Boolean

    var sensitivityMultiplier: Float = 1f
        set(value) {
            field = value
            setScrollSensitivity(value)
        }

    private var orientation: Int

    private val verticalGestureDecorator: GestureDetector by lazy {
        GestureDetector(context, ScrollGestureListener(Orientation.VERTICAL))
    }

    private val horizontalGestureDecorator: GestureDetector by lazy {
        GestureDetector(context, ScrollGestureListener(Orientation.HORIZONTAL))
    }

    init {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BaseViewPager)

        orientation =
            typedArray.getInt(R.styleable.BaseViewPager_orientation, Orientation.HORIZONTAL)

        if (orientation == Orientation.VERTICAL) {
            overScrollMode = OVER_SCROLL_NEVER
            setPageTransformer(true, VerticalPageTransformer())
        }

        isSwipeEnabled =
            typedArray.getBoolean(R.styleable.BaseViewPager_swipeEnabled, true)

        pageMargin =
            typedArray.getDimensionPixelSize(R.styleable.BaseViewPager_pageMargin, 0)

        sensitivityMultiplier =
            typedArray.getFloat(R.styleable.BaseViewPager_sensitivityMultiplier, 1f)

        typedArray.recycle()
    }

    private fun setScrollSensitivity(sensitivityMultiplier: Float) {
        val viewpager: Class<ViewPager> = ViewPager::class.java

        val minDistanceForFlingFieldField: Field = viewpager.getDeclaredField("MIN_DISTANCE_FOR_FLING").apply {
            isAccessible = true
        }
        val flingDistanceFiled: Field = viewpager.getDeclaredField("mFlingDistance").apply {
            isAccessible = true
        }
        val sensitivity: Int = (minDistanceForFlingFieldField.getInt(this) *
                context.resources.displayMetrics.density *
                sensitivityMultiplier).toInt()

        flingDistanceFiled.set(this, sensitivity)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean = try {
        when (isSwipeEnabled) {
            true -> when (orientation) {
                Orientation.VERTICAL -> super.onTouchEvent(swapXY(ev))
                Orientation.HORIZONTAL -> super.onTouchEvent(ev)
                else -> super.onTouchEvent(ev)
            }
            false -> ev?.action != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev)
        }

    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = try {
        when (isSwipeEnabled) {
            true -> when (orientation) {
                Orientation.VERTICAL -> {
                    super.onInterceptTouchEvent(swapXY(ev)).apply {
                        swapXY(ev)
                    }
                    verticalGestureDecorator.onTouchEvent(ev)
                }
                Orientation.HORIZONTAL -> {
                    super.onInterceptTouchEvent(ev)
                    horizontalGestureDecorator.onTouchEvent(ev)
                }
                else -> super.onInterceptTouchEvent(ev)
            }

            false -> when {
                ev?.action != MotionEvent.ACTION_MOVE -> when (super.onInterceptTouchEvent(ev)) {
                    true -> super.onTouchEvent(ev)
                    else -> false
                }
                else -> false
            }
        }

    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        false
    }

    private fun swapXY(motionEvent: MotionEvent?): MotionEvent? = motionEvent?.apply {
        setLocation(
            (motionEvent.y / height) * width,
            (motionEvent.x / width) * height
        )
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return orientation == Orientation.VERTICAL
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return orientation == Orientation.HORIZONTAL
    }

    private class VerticalPageTransformer : PageTransformer {

        override fun transformPage(page: View, position: Float) {
            when {
                position <= 1 -> {
                    page.alpha = 1.0f
                    page.translationX = page.width * -position
                    page.translationY = position * page.height
                }
                else -> page.alpha = 0.0f
            }
        }
    }

    private class ScrollGestureListener(

        val orientation: Int

    ) : GestureDetector.SimpleOnGestureListener() {

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            return when (orientation) {
                Orientation.VERTICAL -> Math.abs(distanceX) < Math.abs(distanceY)
                Orientation.HORIZONTAL -> Math.abs(distanceX) > Math.abs(distanceY)
                else -> super.onScroll(e1, e2, distanceX, distanceY)
            }
        }

    }

}