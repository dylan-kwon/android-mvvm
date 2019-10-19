package dylan.kwon.mvvm.util.base.model

import org.koin.core.KoinComponent
import okhttp3.RequestBody as okHttpRequestBody

open class Request : HashMap<String, okHttpRequestBody>(), KoinComponent {

    companion object {
        const val TRUE: Int = 1
        const val FALSE: Int = 0
    }

    open val query: RequestQuery
        get() = RequestQuery()

    open val body: RequestBody
        get() = RequestBody()

    open val multipart: RequestMultipart
        get() = RequestMultipart()

}

