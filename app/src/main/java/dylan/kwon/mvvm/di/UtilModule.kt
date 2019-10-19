package dylan.kwon.mvvm.di

import dylan.kwon.mvvm.util.*
import org.koin.core.module.Module
import org.koin.dsl.module

object UtilModule {

    @JvmStatic
    val INSTANCE: Module = module {
        single {
            RegexUtil()
        }
        single {
            DisplayUtil(get())
        }
        single {
            FileUtil(get())
        }
        single {
            ResourceProviderImpl(get()) as ResourceProvider
        }
    }

}
