package dylan.kwon.mvvm.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dylan.kwon.mvvm.util.base.model.Dto
import java.util.*

@JsonClass(generateAdapter = true)
data class User(

    @Json(name = Field.ID)
    override var id: Long? = null,

    @Json(name = Field.TYPE)
    var type: UserType? = null,

    @Json(name = Field.STATUS)
    var status: UserStatus? = null,

    @Json(name = Field.NAME)
    var name: String? = null,

    @Json(name = Field.EMAIL)
    var email: String? = null,

    @Json(name = Field.PHONE)
    var phone: String? = null,

    @Json(name = Field.CREATED_AT)
    override var createdAt: Date? = null,

    @Json(name = Field.UPDATED_AT)
    override var updatedAt: Date? = null

) : Dto {

    object Field {
        const val ID: String = "id"
        const val TYPE: String = "type"
        const val STATUS: String = "status"
        const val NAME: String = "name"
        const val EMAIL: String = "email"
        const val PHONE: String = "phone"
        const val CREATED_AT: String = "created_at"
        const val UPDATED_AT: String = "updated_at"
    }

}