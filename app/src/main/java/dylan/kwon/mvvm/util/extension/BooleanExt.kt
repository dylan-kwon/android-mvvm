package dylan.kwon.mvvm.util.extension

fun Boolean?.toInt(): Int = when (this) {
    true -> 1
    else -> 0
}

fun Boolean?.reverse(): Boolean = when (this) {
    true -> false
    null, false -> true
}
