package dylan.kwon.mvvm.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import dylan.kwon.mvvm.data.remote.dto.User
import dylan.kwon.mvvm.util.base.model.Entity
import java.util.*
import androidx.room.Entity as RoomEntity

@RoomEntity(
    tableName = UserEntity.TABLE_NAME
)
data class UserEntity(

    @ColumnInfo(name = Field.ID)
    @PrimaryKey(autoGenerate = true)
    override var id: Long?,

    @ColumnInfo(name = Field.NAME)
    var name: String? = "",

    @ColumnInfo(name = Field.EMAIL)
    var email: String? = "",

    @ColumnInfo(name = Field.PHONE)
    var phone: String? = "",

    @ColumnInfo(name = Field.CREATED_AT)
    override var createdAt: Date = Date(),

    @ColumnInfo(name = Field.UPDATED_AT)
    override var updatedAt: Date = Date()

) : Entity {

    companion object {
        const val TABLE_NAME: String = "user"
    }

    object Field {
        const val ID: String = "_id"
        const val NAME: String = "name"
        const val EMAIL: String = "email"
        const val PHONE: String = "phone"
        const val CREATED_AT: String = "created_at"
        const val UPDATED_AT: String = "updated_at"
    }

    fun toDto(): User = User(
        id = this.id,
        name = this.name,
        email = this.email,
        phone = this.phone,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

}