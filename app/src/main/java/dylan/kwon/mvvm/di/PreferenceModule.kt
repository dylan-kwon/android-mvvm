package dylan.kwon.mvvm.di

import dylan.kwon.mvvm.data.local.preference.AppPreferenceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

object PreferenceModule {

    @JvmStatic
    val INSTANCE: Module = module {
        single {
            AppPreferenceImpl(get())
        }
    }

}
