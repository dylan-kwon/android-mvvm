package dylan.kwon.mvvm.util

import android.content.Context
import dylan.kwon.mvvm.App
import java.io.File

object DirectoryFactory {

    @JvmField
    val TAG: String = DirectoryFactory::class.java.simpleName

    @JvmField
    val FILES_DIR: File = getFilesDirPath()

    @JvmField
    val CACHE_DIR: File = getCacheDirPath()

    @JvmField
    val CACHE_FILES_DIR: File = getCacheFilesDirPath()

    @JvmField
    val CACHE_RETROFIT_DIR: File = getRetrofitCacheDirPath()

    /**
     * 외부 저장소(SDCARD)가 있는 경우 외부 저장소 경로를, 없는 경우 내부 저장소 경로를 반환.
     *
     * @return ./appFilesDir
     */
    @JvmStatic
    private fun getFilesDirPath(context: Context = App.context): File = context.getExternalFilesDir(null)
        ?: context.filesDir
        ?: throw NullPointerException("could not found files directory.")


    /**
     * 외부 저장소(SDCARD)가 있는 경우 외부 캐시 저장소 경로를, 없는 경우 내부 캐시 저장소 경로를 반환.
     *
     * @return ./appCacheDir
     */
    @JvmStatic
    private fun getCacheDirPath(context: Context = App.context): File = context.externalCacheDir
        ?: context.cacheDir
        ?: throw NullPointerException("could not found cache directory.")


    /**
     * 앱 캐시 파일이 저장되는 경로를 반환
     *
     * @return ./appCacheDir/files
     */
    @JvmStatic
    private fun getCacheFilesDirPath(): File = File(CACHE_DIR, "files").also {
        if (!it.exists()) {
            LogUtil.i(TAG, "create cacheFileDir = " + it.mkdirs())
        }
    }

    /**
     * 앱 retrofit cache 파일이 저장되는 경로를 반환
     *
     * @return ./appCacheDir/retrofit
     */
    @JvmStatic
    private fun getRetrofitCacheDirPath(): File = File(CACHE_DIR, "retrofit").also {
        if (!it.exists()) {
            LogUtil.i(TAG, "create retrofitCacheDir = " + it.mkdirs())
        }
    }

}
