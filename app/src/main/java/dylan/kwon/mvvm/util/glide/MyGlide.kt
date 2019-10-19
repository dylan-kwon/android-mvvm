package dylan.kwon.mvvm.util.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyGlide : AppGlideModule() {

    companion object {
        // 1GB
        private const val CACHE_SIZE: Long = 1024 * 1024 * 1024
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, "glide-cache", CACHE_SIZE))
    }

}