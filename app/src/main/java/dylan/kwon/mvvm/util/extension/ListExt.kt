package dylan.kwon.mvvm.util.extension

import dylan.kwon.mvvm.util.base.model.Model

fun <T> List<T>.copy(): List<T> = ArrayList(this)

fun <T> MutableList<T>.toArrayList(): ArrayList<T> = ArrayList(this)

infix fun <T : Model> List<T>.indexOfId(id: Long?): Int {
    if (id == null) {
        return -1
    }
    for ((i: Int, item: T) in withIndex()) {
        if (item.id != id) {
            continue
        }
        return i
    }
    return -1
}

infix fun <T : Model> MutableList<T>.removeById(id: Long?): T? {
    val index: Int = indexOfId(id)
    return if (index >= 0) removeAt(index) else null
}

infix fun <T : Model> List<T>.getById(id: Long?): T? {
    val index: Int = indexOfId(id)
    return if (index >= 0) get(index) else null
}

infix fun <T : Model> List<T>.containsById(id: Long?): Boolean = indexOfId(id) >= 0