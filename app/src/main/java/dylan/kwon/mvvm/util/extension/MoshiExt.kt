package dylan.kwon.mvvm.util.extension

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

inline infix fun <reified T> Moshi.toJson(`object`: T): String? = try {
    this.adapter<T>(T::class.java)?.toJson(`object`)

} catch (e: Exception) {
    e.printStackTrace()
    null
}

inline infix fun <reified T> Moshi.fromJson(json: String): T? = try {
    this.adapter<T>(T::class.java)?.fromJson(json)

} catch (e: Exception) {
    e.printStackTrace()
    null
}

inline infix fun <reified T> Moshi.toMutableList(jsonArray: String?): MutableList<T> {
    if (jsonArray.isNullOrEmpty()) {
        return mutableListOf()
    }
    return try {
        val type: Type = Types.newParameterizedType(List::class.java, T::class.java)
        this.adapter<MutableList<T>>(type)?.fromJson(jsonArray) ?: mutableListOf()

    } catch (e: Exception) {
        e.printStackTrace()
        arrayListOf()
    }
}