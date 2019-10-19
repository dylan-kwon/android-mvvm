package dylan.kwon.mvvm.data.local.db.dao

import androidx.room.Query
import dylan.kwon.mvvm.data.local.db.entity.UserEntity
import dylan.kwon.mvvm.util.base.model.Dao
import io.reactivex.Flowable
import androidx.room.Dao as RoomDao

@RoomDao
interface UserDao : Dao<UserEntity> {

    @Query(
        """
        SELECT
            *
        FROM
            ${UserEntity.TABLE_NAME}
        ORDER BY
            ${UserEntity.Field.ID} ASC
        LIMIT :startIndex, :count
    """
    )
    fun getUsers(startIndex: Int, count: Int): Flowable<List<UserEntity>>


    @Query(
        """
        SELECT
            *
        FROM
            ${UserEntity.TABLE_NAME}
        WHERE
            ${UserEntity.Field.ID} = :userId
    """
    )
    fun getUser(userId: Int): Flowable<UserEntity>

    @Query(
        """
        DELETE
        FROM
            ${UserEntity.TABLE_NAME}
    """
    )
    fun deleteAll()

}