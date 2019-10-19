package dylan.kwon.mvvm.util.base.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ResponseNonData : Response<ResponseNonData.Data>() {

    @JsonClass(generateAdapter = true)
    class Data

}