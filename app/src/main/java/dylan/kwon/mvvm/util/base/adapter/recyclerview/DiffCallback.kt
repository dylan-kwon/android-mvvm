package dylan.kwon.mvvm.util.base.adapter.recyclerview

import androidx.recyclerview.widget.DiffUtil
import dylan.kwon.mvvm.util.base.model.Model

/**
 * Created by seokchan.kwon on 2017. 12. 12..
 * 객체의 equals와 hashcode를 반드시 override해서 사용.
 */
class DiffCallback<T>(

    /**
     * 현재 RecyclerView에 보이고 있는 아아템 리스트
     */
    private val oldItems: List<T>,

    /**
     * 새롭게 교체할 아이템 리스트
     */
    private val newItems: List<T>

) : DiffUtil.Callback() {


    /**
     * @return 이전 아이템의 사이즈를 리턴.
     */
    override fun getOldListSize(): Int = this.oldItems.size


    /**
     * @return 새 아이템의 사이즈를 리턴.
     */
    override fun getNewListSize(): Int = this.newItems.size


    /**
     * oldItem과 newItem이 같은 항목인지 비교 (주로 객체의 id로 비교)
     *
     * @param oldItemPosition 이전 리스트에서 아이템의 포지션.
     * @param newItemPosition 새 리스트에서 아이템의 포지션.
     * @return 같으면 true, 다르면 false.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: T = oldItems[oldItemPosition]
        val newItem: T = newItems[newItemPosition]

        return when (oldItem) {
            is Model -> (oldItem as? Model)?.id == (newItem as? Model)?.id
            else -> oldItem?.hashCode() == newItem?.hashCode()
        }
    }


    /**
     * areItemsTheSame()의 리턴값이 true인 경우에 호출.
     * oldItem과 newItem의 데이터가 같은지 비교 (주로 equals 함수를 오버리이드 해서 사용)
     *
     * @param oldItemPosition 이전 리스트에서 아이템의 포지션.
     * @param newItemPosition 새 리스트에서 아이템의 포지션.
     * @return 같으면 true, 다르면 false.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: T = oldItems[oldItemPosition]
        val newItem: T = newItems[newItemPosition]
        return oldItem == newItem
    }

}
