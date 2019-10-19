package dylan.kwon.mvvm.util.binding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dylan.kwon.mvvm.util.glide.GlideApp
import java.io.File

object ImageViewBindingAdapter {

    enum class ScaleType {
        CENTER_CROP,
        CIRCLE_CROP,
        CENTER_INSIDE
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "image",
            "scaleType",
            "thumbnail",
            "placeHolder",
            "errorPlaceHolder",
            "isCache"
        ], requireAll = false
    )
    fun bindImage(
        imageView: ImageView,
        image: Any? = null,
        scaleType: ScaleType? = null,
        thumbnail: Float? = null,
        placeHolder: Drawable? = null,
        errorPlaceHolder: Drawable? = null,
        isCache: Boolean? = null
    ) {
        GlideApp.with(imageView)
            .run {
                when (image) {
                    is Int -> load(image)
                    is Uri -> load(image)
                    is File -> load(image)
                    is String -> load(image)
                    is Bitmap -> load(image)
                    is Drawable -> load(image)
                    is ByteArray -> load(image)
                    else -> load(image)
                }
            }
            .run {
                when (scaleType) {
                    ScaleType.CENTER_CROP -> centerCrop()
                    ScaleType.CIRCLE_CROP -> circleCrop()
                    else -> centerInside()
                }
            }
            .apply {
                if (thumbnail != null) {
                    thumbnail(thumbnail)
                }
            }
            .apply {
                if (isCache == false) {
                    diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                }
            }
            .placeholder(placeHolder)
            .error(errorPlaceHolder)
            .into(imageView)
    }

}
