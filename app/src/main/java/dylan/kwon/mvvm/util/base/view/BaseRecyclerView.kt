package dylan.kwon.mvvm.util.base.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import dylan.kwon.mvvm.R

class BaseRecyclerView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0

) : RecyclerView(context, attrs, defStyle) {

    private var mMaxHeight: Int = 0

    init {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView)

        mMaxHeight =
            typedArray.getLayoutDimension(R.styleable.BaseRecyclerView_maxHeight, mMaxHeight)

        typedArray.recycle()
    }

    fun setMaxHeight(maxHeight: Int) {
        this.mMaxHeight = maxHeight
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isInEditMode) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        if (mMaxHeight > 0) {
            super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST)
            )
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val requestCancelDisallowInterceptTouchEvent: Boolean = scrollState == SCROLL_STATE_SETTLING
        val consumed: Boolean = super.onInterceptTouchEvent(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> if (requestCancelDisallowInterceptTouchEvent) {
                parent.requestDisallowInterceptTouchEvent(false)

                if (!canScrollVertically(-1) || !canScrollVertically(1)) {
                    stopScroll()
                    return false
                }
            }
        }
        return consumed
    }

}