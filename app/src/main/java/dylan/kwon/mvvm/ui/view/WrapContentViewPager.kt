package dylan.kwon.mvvm.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class WrapContentViewPager @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null

) : ViewPager(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mode: Int = MeasureSpec.getMode(heightMeasureSpec)
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            var height = 0

            for (i: Int in 0 until childCount) {
                val child: View = getChildAt(i)
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))

                if (child.measuredHeight > height) {
                    height = child.measuredHeight
                }
            }
            super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            )

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}
