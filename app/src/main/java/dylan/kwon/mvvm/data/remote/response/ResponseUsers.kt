package dylan.kwon.mvvm.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dylan.kwon.mvvm.data.remote.dto.User
import dylan.kwon.mvvm.util.base.model.Response

@JsonClass(generateAdapter = true)
class ResponseUsers : Response<ResponseUsers.Data>() {

    @JsonClass(generateAdapter = true)
    class Data {

        object Field {
            const val USERS: String = "users"
            const val IS_PAGE_END: String = "is_page_end"
        }

        @Json(name = Field.USERS)
        var users: MutableList<User>? = null

        @Json(name = Field.IS_PAGE_END)
        var isPageEnd: Boolean? = null

    }

}