package dylan.kwon.mvvm.util.base.model

import dylan.kwon.mvvm.util.network.RetrofitUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject

open class RequestMultipart : HashMap<String, RequestBody>(), KoinComponent {

    val retrofitUtil: RetrofitUtil by inject()

}
