package dylan.kwon.mvvm.di

import dylan.kwon.mvvm.domain.user.GetUsersUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

object UseCaseModule {

    @JvmStatic
    val INSTANCE: Module = module {
        factory {
            GetUsersUseCase(
                get()
            )
        }
    }

}
