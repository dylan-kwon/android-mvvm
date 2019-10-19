package dylan.kwon.mvvm.util.base.model

import com.squareup.moshi.Json

open class Response<DATA> {

    companion object {
        const val TRUE: Int = 1
        const val FALSE: Int = 0
    }

    object Code {
        const val NETWORK_ERROR: Int = -1
        const val SUCCESS: Int = 200
        const val FAIL: Int = 210
    }

    object Field {
        const val RESULT_CODE: String = "response_code"
        const val RESULT_MESSAGE: String = "response_message"
        const val RESULT_DATA: String = "response_data"
    }

    @Json(name = Field.RESULT_CODE)
    var code: Int? = null

    @Json(name = Field.RESULT_MESSAGE)
    var message: String? = null

    @Json(name = Field.RESULT_DATA)
    var data: DATA? = null

    val isNetworkError: Boolean
        get() = code == Code.NETWORK_ERROR

    inline fun check(
        onSuccess: () -> Unit,
        noinline onFail: (() -> Unit)? = null
    ) {
        when (code) {
            Code.SUCCESS -> onSuccess()
            else -> onFail?.let {
                it()
            }
        }
    }

}
