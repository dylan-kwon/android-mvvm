package dylan.kwon.mvvm.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingChild2
import androidx.core.view.NestedScrollingChildHelper
import dylan.kwon.mvvm.R

class NestedCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr), NestedScrollingChild2 {

    private val childHelper = NestedScrollingChildHelper(this)

    var preScrollUp: PreScrollStrategy
    var preScrollDown: PreScrollStrategy

    init {
        isNestedScrollingEnabled = true

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NestedCoordinatorLayout)
        preScrollUp = PreScrollStrategy.findByParam(
            typedArray.getInt(R.styleable.NestedCoordinatorLayout_preScrollUp, 0)
        )
        preScrollDown = PreScrollStrategy.findByParam(
            typedArray.getInt(R.styleable.NestedCoordinatorLayout_preScrollDown, 0)
        )
        typedArray.recycle()
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        val handled = super.onStartNestedScroll(child, target, axes, type)
        return startNestedScroll(axes, type) || handled
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (dy > 0) {
            when (preScrollUp) {
                PreScrollStrategy.BOTH -> handleNestedPreScrollBoth(target, dx, dy, consumed, type)
                PreScrollStrategy.PARENT_FIRST -> handleNestedPreScrollParentFirst(target, dx, dy, consumed, type)
                PreScrollStrategy.CHILD_FIRST -> handleNestedPreScrollChildFirst(target, dx, dy, consumed, type)
            }
        } else {
            when (preScrollDown) {
                PreScrollStrategy.BOTH -> handleNestedPreScrollBoth(target, dx, dy, consumed, type)
                PreScrollStrategy.PARENT_FIRST -> handleNestedPreScrollParentFirst(target, dx, dy, consumed, type)
                PreScrollStrategy.CHILD_FIRST -> handleNestedPreScrollChildFirst(target, dx, dy, consumed, type)
            }
        }
    }

    private fun handleNestedPreScrollBoth(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val consumedByParent = IntArray(2)
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        dispatchNestedPreScroll(dx, dy, consumedByParent, null, type)

        consumed[0] += consumedByParent[0]
        consumed[1] += consumedByParent[1]
    }

    private fun handleNestedPreScrollParentFirst(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val consumedByParent = IntArray(2)
        dispatchNestedPreScroll(dx, dy, consumedByParent, null, type)
        super.onNestedPreScroll(target, dx - consumedByParent[0], dy - consumedByParent[1], consumed, type)

        consumed[0] += consumedByParent[0]
        consumed[1] += consumedByParent[1]
    }

    private fun handleNestedPreScrollChildFirst(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val consumedByParent = IntArray(2)
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], consumedByParent, null, type)

        consumed[0] += consumedByParent[0]
        consumed[1] += consumedByParent[1]
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, null, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        super.onStopNestedScroll(target, type)
        stopNestedScroll(type)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        val reacted = super.onNestedPreFling(target, velocityX, velocityY)
        return dispatchNestedPreFling(velocityX, velocityY) || reacted
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        val reacted = super.onNestedFling(target, velocityX, velocityY, consumed)
        return dispatchNestedFling(velocityX, velocityY, consumed) || reacted
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return childHelper.isNestedScrollingEnabled
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        childHelper.isNestedScrollingEnabled = enabled
    }

    override fun hasNestedScrollingParent(): Boolean {
        return childHelper.hasNestedScrollingParent()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return childHelper.hasNestedScrollingParent(type)
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return childHelper.startNestedScroll(axes)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return childHelper.startNestedScroll(axes, type)
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?): Boolean {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?, type: Int): Boolean {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int): Boolean {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type)
    }

    override fun stopNestedScroll() {
        childHelper.stopNestedScroll()
    }

    override fun stopNestedScroll(type: Int) {
        childHelper.stopNestedScroll(type)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    enum class PreScrollStrategy(val param: Int) {
        BOTH(0),
        PARENT_FIRST(1),
        CHILD_FIRST(2);

        companion object {
            fun findByParam(param: Int): PreScrollStrategy =
                values().singleOrNull { it.param == param } ?: BOTH
        }
    }
}
