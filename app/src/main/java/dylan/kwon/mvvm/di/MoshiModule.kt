package dylan.kwon.mvvm.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dylan.kwon.mvvm.util.moshi.DateAdapter
import org.koin.core.module.Module
import org.koin.dsl.module

object MoshiModule {

    @JvmStatic
    val INSTANCE: Module = module {
        single {
            createMoshi()
        }
    }

    @JvmStatic
    private fun createMoshi(): Moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

}
