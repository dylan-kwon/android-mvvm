package dylan.kwon.mvvm.util.network

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File

class RetrofitUtil {

    val applicationJson: MediaType = MediaType.parse("application/json;")!!

    val multipartFormData: MediaType = MediaType.parse("multipart/form-data")!!

    fun createRequestBody(value: String): RequestBody =
        RequestBody.create(applicationJson, value)

    fun createRequestBody(value: ByteArray): RequestBody =
        RequestBody.create(applicationJson, value)

    fun createRequestBody(value: Int = 0): RequestBody =
        RequestBody.create(applicationJson, value.toString())

    fun createRequestBody(value: Boolean): RequestBody =
        RequestBody.create(applicationJson, value.toString())

    fun createRequestBody(file: File): RequestBody =
        RequestBody.create(multipartFormData, file)

    fun createMultiPartBody(key: String, file: File): MultipartBody.Part =
        MultipartBody.Part.createFormData(
            key,
            file.name,
            createRequestBody(file)
        )

    fun createMultiPartKey(key: String, file: File): String =
        createMultiPartKey(key, file.name)

    fun createMultiPartKey(key: String, fileName: String): String =
        "$key\"; filename=\"$fileName\""

    fun createResponseBody(json: String): ResponseBody =
        ResponseBody.create(applicationJson, json)

}
