package dylan.kwon.mvvm.di

import dylan.kwon.mvvm.ui.dialog.popup.PopupViewModel
import dylan.kwon.mvvm.ui.dialog.progress.ProgressViewModel
import dylan.kwon.mvvm.ui.screen.main.MainViewModel
import dylan.kwon.mvvm.ui.screen.recyclerview.RecyclerViewModel
import dylan.kwon.mvvm.ui.screen.viewpager.ViewPagerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ViewModelModule {

    @JvmStatic
    val INSTANCE: Module = module {
        viewModel {
            MainViewModel(
                get()
            )
        }
        viewModel {
            RecyclerViewModel(
                get()
            )
        }
        viewModel {
            ViewPagerViewModel(
                get()
            )
        }
        viewModel {
            PopupViewModel()
        }
        viewModel {
            ProgressViewModel()
        }
    }
}
