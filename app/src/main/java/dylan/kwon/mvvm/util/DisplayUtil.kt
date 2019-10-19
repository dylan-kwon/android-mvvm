package dylan.kwon.mvvm.util

import android.content.Context
import android.util.TypedValue
import android.view.View

class DisplayUtil(private val applicationContext: Context) {

    /**
     * 디바이스 가로 픽셀 사이즈
     */
    val screenWidth: Int by lazy {
        applicationContext.resources.displayMetrics.widthPixels
    }

    /**
     * 디바이스 세로 픽셀 사이즈 (스테이터스바 영역 포함, 소프트웨어 키보드 영역 제외)
     */
    val screenHeight: Int by lazy {
        applicationContext.resources.displayMetrics.heightPixels
    }

    /**
     * 디바이스 세로 픽셀 사이즈 (스테이터스바 영역 포함, 소프트웨어 키보드 영역 제외)
     */
    val screenHeightExcludeStatusBar: Int by lazy {
        applicationContext.resources.displayMetrics.heightPixels
    }

    /**
     * 스테이터스바 높이 픽셀 사이즈
     */
    val statusBarHeight: Int by lazy {
        val resourceId: Int = applicationContext.resources.getIdentifier(
            "status_bar_height", "dimen", "android"
        )
        applicationContext.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 디바이스 세로 사이즈
     * @param includeStatusBar 스테이터스바 영역 포함 여부
     */
    fun getScreenHeight(includeStatusBar: Boolean = false): Int {
        if (includeStatusBar) {
            return screenHeight
        }
        return screenHeight - statusBarHeight
    }

    /**
     * 뷰의 절대 좌표 X
     */
    fun getAbsoluteX(view: View): Int {
        return IntArray(2).let {
            view.getLocationOnScreen(it)
            return@let it[0]
        }
    }

    /**
     * 뷰의 절대 좌표 Y (스테이터스바 영역 포함)
     * @param includeStatusBar 스테이터스바 영역 포함 여부
     */
    fun getAbsoluteY(view: View, includeStatusBar: Boolean = false): Int {
        val absoluteY: Int = IntArray(2).let {
            view.getLocationOnScreen(it)
            return@let it[1]
        }
        if (includeStatusBar) {
            return absoluteY
        }
        return absoluteY - statusBarHeight
    }

    /**
     * DP to PX
     */
    fun dpToPx(dp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, applicationContext.resources.displayMetrics)

}
