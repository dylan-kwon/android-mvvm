package dylan.kwon.mvvm.util.extension

fun <K, V> Map<K, V>.copy(): Map<K, V> = HashMap<K, V>(this)
