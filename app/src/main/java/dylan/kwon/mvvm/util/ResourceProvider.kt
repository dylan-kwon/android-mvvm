package dylan.kwon.mvvm.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

interface ResourceProvider {

    fun getString(resId: Int): String

    fun getStringArray(resId: Int): Array<String>

    fun getInteger(resId: Int): Int

    fun getIntArray(resId: Int): IntArray

    fun getFloat(resId: Int): Float

    fun getBoolean(resId: Int): Boolean

    fun getColor(resId: Int): Int

    fun getDimension(resId: Int): Float

    fun getDimensionPixelSize(resId: Int): Int

}

class ResourceProviderImpl(

    val applicationContext: Context

) : ResourceProvider {

    override fun getString(resId: Int): String =
        applicationContext.getString(resId)

    override fun getStringArray(resId: Int): Array<String> =
        applicationContext.resources.getStringArray(resId)

    override fun getInteger(resId: Int): Int =
        applicationContext.resources.getInteger(resId)

    override fun getIntArray(resId: Int): IntArray =
        applicationContext.resources.getIntArray(resId)

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun getFloat(resId: Int): Float =
        applicationContext.resources.getFloat(resId)

    override fun getBoolean(resId: Int): Boolean =
        applicationContext.resources.getBoolean(resId)

    override fun getColor(resId: Int): Int =
        ContextCompat.getColor(applicationContext, resId)

    override fun getDimension(resId: Int): Float =
        applicationContext.resources.getDimension(resId)

    override fun getDimensionPixelSize(resId: Int): Int =
        applicationContext.resources.getDimensionPixelSize(resId)

}