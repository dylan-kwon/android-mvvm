package dylan.kwon.mvvm.util

import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import dylan.kwon.mvvm.App
import dylan.kwon.mvvm.R

object FontFactory {

    @JvmField
    val NANUM_BARUN_GOTHIC: Typeface? =
        ResourcesCompat.getFont(App.context, R.font.font_nanum_barun_gothic)

    @JvmStatic
    fun getDefaultFont(): Typeface? = NANUM_BARUN_GOTHIC

}