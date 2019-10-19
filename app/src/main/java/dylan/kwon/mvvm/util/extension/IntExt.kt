package dylan.kwon.mvvm.util.extension

fun Int?.toBoolean(): Boolean = when {
    this == null -> false
    this <= 0 -> false
    else -> true
}

fun Int?.reverseBoolean(): Int = toBoolean().reverse().toInt()
