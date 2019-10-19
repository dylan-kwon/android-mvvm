package dylan.kwon.mvvm.util.base.adapter.viewpager

abstract class LoopPagerAdapter<T : Any> : BasePagerAdapter<T>() {

    /**
     * ViewPager에 생성되는 아이템의 개수
     */
    override fun getCount(): Int {
        if (this.items.size > 1) {
            return Integer.MAX_VALUE
        }
        if (this.items.size == 1) {
            return 1
        }
        return 0
    }

    /**
     * 실제 아이템의 개수
     */
    fun getItemCount(): Int = this.items.size

    /**
     * ViewPager의 포지션에 해당하는 실제 아이템의 포지션
     */
    fun getItemIndex(position: Int) = position % getItemCount()

}
