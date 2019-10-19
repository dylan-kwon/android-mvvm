package dylan.kwon.mvvm.util.base.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface Dao<Entity> {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(entity: Entity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(entity: Entity)

    @Delete
    fun delete(entity: Entity)

}
