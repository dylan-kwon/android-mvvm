package dylan.kwon.mvvm.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dylan.kwon.mvvm.data.remote.dto.User
import dylan.kwon.mvvm.util.base.model.Response

@JsonClass(generateAdapter = true)
class ResponseUser : Response<ResponseUser.Data>() {

    @JsonClass(generateAdapter = true)
    class Data {

        object Field {
            const val USER: String = "user"
        }

        @Json(name = Field.USER)
        var user: User? = null

    }

}