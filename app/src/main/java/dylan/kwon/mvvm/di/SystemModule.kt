package dylan.kwon.mvvm.di

import dylan.kwon.mvvm.util.system.*
import org.koin.core.module.Module
import org.koin.dsl.module

object SystemModule {

    @JvmStatic
    val INSTANCE: Module = module {
        single {
            KeyboardUtil(get())
        }
        single {
            ClipboardUtil(get())
        }
        single {
            ActivityUtil(get())
        }
        single {
            TelephonyUtil(get())
        }
        single {
            ConnectivityUtil(get())
        }
        single {
            NotificationUtil(get())
        }
        factory {
            LocationUtil(get())
        }
    }

}
