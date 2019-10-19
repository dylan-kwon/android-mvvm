package dylan.kwon.mvvm.util.base.model

import dylan.kwon.mvvm.util.network.RetrofitUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject

open class RequestBody : RequestBody(), KoinComponent {

    val retrofitUtil: RetrofitUtil by inject()

    private val jsonObject: JSONObject = JSONObject()

    private val body: RequestBody by lazy {
        retrofitUtil.createRequestBody(jsonObject.toString())
    }

    fun <V> put(key: String, value: V?) {
        jsonObject.put(key, value)
    }

    fun get(key: String, fallback: String): String =
        jsonObject.optString(key, fallback)

    override fun contentType(): MediaType? {
        return body.contentType()
    }

    override fun writeTo(sink: BufferedSink) {
        body.writeTo(sink)
    }

    override fun contentLength(): Long {
        return body.contentLength()
    }

}
