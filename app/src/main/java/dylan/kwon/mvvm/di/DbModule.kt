package dylan.kwon.mvvm.di

import android.content.Context
import androidx.room.Room
import dylan.kwon.mvvm.data.local.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

object DbModule {

    @JvmStatic
    val INSTANCE: Module = module {
        single {
            createDatabase(get())
        }
    }

    @JvmStatic
    fun createDatabase(applicationContext: Context): AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        AppDatabase.NAME
    ).build()

}
