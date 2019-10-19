package dylan.kwon.mvvm.di

import android.content.Context
import dylan.kwon.mvvm.App
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {

    @JvmStatic
    val INSTANCE: Module = module {
        single {
            get<Context>() as App
        }
    }

    @JvmStatic
    fun getModules(): List<Module> = listOf(
        INSTANCE,
        UtilModule.INSTANCE,
        SystemModule.INSTANCE,
        DbModule.INSTANCE,
        PreferenceModule.INSTANCE,
        MoshiModule.INSTANCE,
        NetworkModule.INSTANCE,
        ViewModelModule.INSTANCE,
        UseCaseModule.INSTANCE,
        RepositoryModule.INSTANCE
    )

}
