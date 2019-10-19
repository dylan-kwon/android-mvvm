package dylan.kwon.mvvm.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dylan.kwon.mvvm.Constant
import dylan.kwon.mvvm.data.local.db.converter.DateConverter
import dylan.kwon.mvvm.data.local.db.dao.UserDao
import dylan.kwon.mvvm.data.local.db.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = AppDatabase.VERSION
)
@TypeConverters(
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @JvmStatic
        val NAME: String = "${Constant.TAG}.db"

        const val VERSION: Int = 1
        const val COUNT_PER_PAGE: Int = 30
    }

    abstract val userDao: UserDao

}
