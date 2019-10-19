package dylan.kwon.mvvm.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Images.Media
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class FileUtil(

    private val applicationContext: Context

) {

    companion object {

        @JvmField
        val TAG: String = FileUtil::class.java.simpleName

        const val RESIZE_PIXEL_SIZE: Int = 1080
    }

    val cacheDir: File = DirectoryFactory.CACHE_FILES_DIR

    val authority: String = "${applicationContext.packageName}.fileprovider"

    fun fileToUri(file: File): Uri = Uri.fromFile(file)

    fun uriToFile(uri: Uri): File = File(uri.path ?: "")

    fun resizeFile(uri: String, resizePx: Int = RESIZE_PIXEL_SIZE): Single<File> = resizeFile(Uri.parse(uri), resizePx)

    fun resizeFile(file: File, resizePx: Int = RESIZE_PIXEL_SIZE): Single<File> = resizeFile(Uri.fromFile(file), resizePx)

    fun resizeFile(uri: Uri, resizePx: Int = RESIZE_PIXEL_SIZE): Single<File> = uriToBitmap(uri)
        .flatMap { bitmap ->
            return@flatMap getImageOrientation(uri)
                .flatMap { orientation ->
                    rotateBitmap(bitmap, orientation)
                }
        }
        .flatMap { bitmap ->
            return@flatMap resizeBitmap(bitmap, resizePx)
        }
        .flatMap { bitmap ->
            return@flatMap bitmapToFile(bitmap)
        }

    inline fun <reified T> resizeFiles(uris: List<T>, resizePx: Int = RESIZE_PIXEL_SIZE): Single<MutableList<File>> = Single.create<MutableList<Single<File>>> {
        val singles: MutableList<Single<File>> = mutableListOf()
        for (uri: T in uris) {
            singles += when (uri) {
                is Uri -> resizeFile(uri, resizePx)
                is File -> resizeFile(uri, resizePx)
                is String -> resizeFile(uri, resizePx)
                else -> throw ClassCastException("only String, Uri, File are allowed.")
            }
        }
        it.onSuccess(singles)

    }.flatMap { singles ->
        return@flatMap Single.zip(singles) {
            val files: MutableList<File> = mutableListOf()
            for (file: Any in it) {
                files += file as? File ?: continue
            }
            return@zip files
        }
    }

    fun cropFile(uri: String, width: Int, height: Int): Single<File> = cropFile(Uri.parse(uri), width, height)

    fun cropFile(file: File, width: Int, height: Int): Single<File> = cropFile(Uri.fromFile(file), width, height)

    fun cropFile(uri: Uri, width: Int, height: Int): Single<File> = uriToBitmap(uri)
        .flatMap { bitmap ->
            return@flatMap getImageOrientation(uri)
                .flatMap { orientation ->
                    rotateBitmap(bitmap, orientation)
                }
        }
        .flatMap { bitmap ->
            return@flatMap cropBitmap(bitmap, width, height)
        }
        .flatMap { bitmap ->
            return@flatMap bitmapToFile(bitmap)
        }

    inline fun <reified T> cropFiles(uris: List<T>, width: Int, height: Int): Single<MutableList<File>> = Single.create<MutableList<Single<File>>> {
        val singles: MutableList<Single<File>> = mutableListOf()
        for (uri: T in uris) {
            singles += when (uri) {
                is Uri -> cropFile(uri, width, height)
                is File -> cropFile(uri, width, height)
                is String -> cropFile(uri, width, height)
                else -> throw ClassCastException("only String, Uri, File are allowed.")
            }
        }
        it.onSuccess(singles)

    }.flatMap { singles ->
        return@flatMap Single.zip(singles) {
            val files: MutableList<File> = mutableListOf()
            for (file: Any in it) {
                files += file as? File ?: continue
            }
            return@zip files
        }
    }

    fun getImageOrientation(uri: Uri): Single<Int> = Single.create {
        val contentResolver: ContentResolver = applicationContext.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)

        if (inputStream == null) {
            it.onSuccess(-1)
            return@create
        }
        val exifInterface: ExifInterface = ExifInterface(inputStream)
        val orientation: Int = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        it.onSuccess(orientation)
    }

    fun urlToBitmap(url: String): Single<Bitmap> = Single.fromCallable {
        val connection: HttpURLConnection? = (URL(url).openConnection() as? HttpURLConnection)?.apply {
            doInput = true
            connect()
        }
        val input: InputStream? = connection?.inputStream
        BitmapFactory.decodeStream(input)
    }

    fun uriToBitmap(uri: Uri): Single<Bitmap> = Single.create {
        val contentResolver: ContentResolver = applicationContext.contentResolver
        val bitmap: Bitmap = Media.getBitmap(contentResolver, uri)
        it.onSuccess(bitmap)
    }

    fun fileToBitmap(file: File): Single<Bitmap> = Single.create {
        val bitmap: Bitmap = BitmapFactory.decodeFile(file.path)
        it.onSuccess(bitmap)
    }

    fun rotateBitmap(bitmap: Bitmap, orientation: Int): Single<Bitmap> = Single.create {
        val degrees: Float = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> {
                it.onSuccess(bitmap)
                return@create
            }
        }
        val matrix: Matrix = Matrix().apply {
            setRotate(degrees, (bitmap.width / 2).toFloat(), (bitmap.height / 2).toFloat())
        }
        val rotateBitmap: Bitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        bitmap.recycle()
        it.onSuccess(rotateBitmap)
    }

    fun resizeBitmap(bitmap: Bitmap, scalePx: Int): Single<Bitmap> = when (bitmap.width > bitmap.height) {
        true -> resizeBitmap(bitmap, (bitmap.width * scalePx) / bitmap.height, scalePx)
        false -> resizeBitmap(bitmap, scalePx, (bitmap.height * scalePx) / bitmap.width)
    }

    fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Single<Bitmap> = Single.create {
        val resizeBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
        bitmap.recycle()
        it.onSuccess(resizeBitmap)
    }

    fun cropBitmap(bitmap: Bitmap, width: Int, height: Int): Single<Bitmap> = Single.create {
        val x: Int = when (bitmap.width > width) {
            true -> (bitmap.width - width) / 2
            else -> 0
        }
        val y: Int = when (bitmap.height > height) {
            true -> (bitmap.height - height) / 2
            else -> 0
        }
        val croppedBitmap: Bitmap = Bitmap.createBitmap(bitmap, x, y, width, height)
        bitmap.recycle()
        it.onSuccess(croppedBitmap)
    }

    @JvmOverloads
    fun bitmapToFile(
        bitmap: Bitmap,
        createFileName: String = "${System.currentTimeMillis()}.jpg"
    ): Single<File> = Single.create {
        val dir: File = cacheDir.apply {
            if (!exists()) mkdirs()
        }
        val file: File = File(dir, createFileName).apply {
            LogUtil.d(TAG, "$createFileName isCreated: ${createNewFile()}")
        }
        FileOutputStream(file).also { fileOutputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.close()
        }
        bitmap.recycle()
        it.onSuccess(file)
    }

    @JvmOverloads
    fun bytesToFile(
        byteArray: ByteArray,
        createFileName: String = "${System.currentTimeMillis()}.jpg"
    ): Single<File> = Single.create {
        val dir: File = cacheDir.apply {
            if (!exists()) mkdirs()
        }
        val file: File = File(dir, createFileName).apply {
            LogUtil.d(TAG, "$createFileName isCreated: ${createNewFile()}")
        }
        FileOutputStream(file).also { fileOutputStream ->
            fileOutputStream.write(byteArray)
            fileOutputStream.close()
        }
        it.onSuccess(file)
    }

    fun getDir(uri: Uri): Single<File> = getDir(uri.path ?: "")

    fun getDir(uri: String): Single<File> = Single.create {
        val file: File = File(uri)
        if (!file.exists()) {
            LogUtil.i(TAG, "mkDirs result = " + file.mkdirs())
        }
        it.onSuccess(file)
    }

    fun deleteFile(uri: Uri): Single<Boolean> = deleteFile(File(uri.path ?: ""))

    fun deleteFile(uri: String): Single<Boolean> = deleteFile(File(uri))

    fun deleteFile(file: File): Single<Boolean> = Single.create<Boolean> {
        it.onSuccess(file.delete())

    }.map { isDeleted ->
        LogUtil.d(TAG, "${file.name} is isDeleted: $isDeleted")
        return@map isDeleted
    }

    inline fun <reified T> deleteFiles(files: List<T>): Single<Unit> = Single.create<MutableList<Single<Boolean>>> {
        val singles: MutableList<Single<Boolean>> = mutableListOf()
        for (file: T in files) {
            singles += when (file) {
                is File -> deleteFile(file)
                is Uri -> deleteFile(file)
                is String -> deleteFile(file)
                else -> throw ClassCastException("only String, Uri, File are allowed.")
            }
        }
        it.onSuccess(singles)

    }.flatMap { singles ->
        return@flatMap Single.zip(singles) {
            return@zip
        }
    }

    fun deleteChildFiles(dirPath: String) = deleteChildFiles(File(dirPath))

    fun deleteChildFiles(uri: Uri) = deleteChildFiles(File(uri.path ?: ""))

    fun deleteChildFiles(dir: File): Completable = Completable.create { emitter ->
        val singles: MutableList<Single<Boolean>> = mutableListOf()
        for (file: File in dir.listFiles() ?: arrayOf()) {
            singles += deleteFile(file)
        }
        Single.zip(singles) {
            emitter.onComplete()
        }
    }

    fun provideUriFromFile(file: File): Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(
            applicationContext, authority, file
        )
    } else {
        fileToUri(file)
    }

}
