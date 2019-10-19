package dylan.kwon.mvvm.util.base.model

import androidx.recyclerview.widget.RecyclerView

data class Pagination(

    var page: Int = FIRST_PAGE,
    var isLock: Boolean = false,
    var isLastPage: Boolean = true,
    var isLastPosition: Boolean = false,
    val isReverse: Boolean = false

) {

    companion object {
        const val FIRST_PAGE: Int = 1
    }

    val isFirstPage: Boolean
        get() = page == FIRST_PAGE

    fun startApi(isResetPage: Boolean = false): Boolean {
        if (isLock) {
            return false
        }
        if (isResetPage) {
            clear()
        }
        lock()
        return true
    }

    fun endApi(isLastPage: Boolean) {
        unLock()
        addPage()
        this.isLastPage = isLastPage
    }

    fun onScrolled(itemCount: Int, firstVisiblePosition: Int, lastVisiblePosition: Int, dx: Int, dy: Int) {
        isLastPosition = when (isReverse) {
            true -> firstVisiblePosition == 0
            false -> lastVisiblePosition == itemCount - 1
        }
    }

    fun onScrollStateChanged(newState: Int, canApi: () -> Unit) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) {
            return
        }
        if (isLock) {
            return
        }
        if (isLastPage) {
            return
        }
        if (!isLastPosition) {
            return
        }
        canApi()
    }

    fun lock() {
        isLock = true
    }

    fun unLock() {
        isLock = false
    }

    fun addPage() {
        page++
    }

    fun clear() {
        unLock()
        page = FIRST_PAGE
        isLastPage = false
        isLastPosition = false
    }

}
