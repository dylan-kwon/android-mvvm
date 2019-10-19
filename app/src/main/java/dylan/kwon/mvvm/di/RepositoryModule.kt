package dylan.kwon.mvvm.di

import dylan.kwon.mvvm.data.local.db.AppDatabase
import dylan.kwon.mvvm.data.repository.UserRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

object RepositoryModule {

    @JvmStatic
    val INSTANCE: Module = module {
        single {
            UserRepository(
                createApi(get()),
                get<AppDatabase>().userDao
            )
        }
    }

    @JvmStatic
    private inline fun <reified T> createApi(retrofit: Retrofit): T =
        retrofit.create(T::class.java)

}
