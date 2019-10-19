package dylan.kwon.mvvm.util.extension

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable

/**
 * Bundle
 */
fun Bundle.putStringMutableList(key: String, value: MutableList<String>?) {
    this.putStringArrayList(key, value?.toArrayList())
}

fun Bundle.getStringMutableList(key: String): MutableList<String>? {
    return this.getStringArrayList(key)
}

fun Bundle.putIntegerMutableList(key: String, value: MutableList<Int>?) {
    this.putIntegerArrayList(key, value?.toArrayList())
}

fun Bundle.getIntegerMutableList(key: String): MutableList<Int>? {
    return this.getIntegerArrayList(key)
}

fun <T : Parcelable> Bundle.putParcelableMutableList(key: String, value: MutableList<out T>?) {
    this.putParcelableArrayList(key, value?.toArrayList())
}

fun <T : Parcelable> Bundle.getParcelableMutableList(key: String): MutableList<T>? {
    return this.getParcelableArrayList(key)
}

fun Bundle.putCharSequenceMutableList(key: String, value: MutableList<CharSequence>?) {
    this.putCharSequenceArrayList(key, value?.toArrayList())
}

fun Bundle.getCharSequenceMutableList(key: String): MutableList<CharSequence>? {
    return this.getCharSequenceArrayList(key)
}


/**
 * Intent
 */
fun Intent.putStringMutableListExtra(key: String, value: MutableList<String>?) {
    this.putStringArrayListExtra(key, value?.toArrayList())
}

fun Intent.getStringMutableListExtra(key: String): MutableList<String>? {
    return this.getStringArrayListExtra(key)
}

fun Intent.putIntegerMutableListExtra(key: String, value: MutableList<Int>?) {
    this.putIntegerArrayListExtra(key, value?.toArrayList())
}

fun Intent.getIntegerMutableListExtra(key: String): MutableList<Int>? {
    return this.getIntegerArrayListExtra(key)
}

fun <T : Parcelable> Intent.putParcelableMutableListExtra(key: String, value: MutableList<out T>?) {
    this.putParcelableArrayListExtra(key, value?.toArrayList())
}

fun <T : Parcelable> Intent.getParcelableMutableListExtra(key: String): MutableList<T>? {
    return this.getParcelableArrayListExtra(key)
}

fun Intent.putCharSequenceMutableListExtra(key: String, value: MutableList<CharSequence>?) {
    this.putCharSequenceArrayListExtra(key, value?.toArrayList())
}

fun Intent.getCharSequenceMutableListExtra(key: String): MutableList<CharSequence>? {
    return this.getCharSequenceArrayListExtra(key)
}