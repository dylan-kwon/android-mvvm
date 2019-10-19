package dylan.kwon.mvvm.data.remote.dto

import com.squareup.moshi.Json
import dylan.kwon.mvvm.util.base.model.Dto
import java.util.*

data class UserType(

    @Json(name = Field.ID)
    override var id: Long? = NONE_ID,

    @Json(name = Field.DESCRIPTION)
    var description: String? = null,

    @Json(name = Field.CREATED_AT)
    override var createdAt: Date? = null,

    @Json(name = Field.UPDATED_AT)
    override var updatedAt: Date? = null

) : Dto {

    companion object {
        const val NONE_ID: Long = 0L
    }

    object Field {
        const val ID: String = "id"
        const val DESCRIPTION: String = "description"
        const val CREATED_AT: String = "created_at"
        const val UPDATED_AT: String = "updated_at"
    }

}