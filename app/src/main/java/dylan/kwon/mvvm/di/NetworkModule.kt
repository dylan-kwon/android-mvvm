package dylan.kwon.mvvm.di

import com.squareup.moshi.Moshi
import dylan.kwon.mvvm.Constant
import dylan.kwon.mvvm.util.DirectoryFactory
import dylan.kwon.mvvm.util.network.RetrofitInterceptor
import dylan.kwon.mvvm.util.network.RetrofitUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    enum class Server(val url: String) {
        REAL("http://10.0.2.2/"),
        TEST("http://10.0.2.2/"),
        LOCAL("http://10.0.2.2:5000/")
    }

    private const val TIME_OUT: Long = 10_000

    private const val CACHE_SIZE: Long = 10 * 1024 * 1024

    @JvmStatic
    val API_SERVER_URL: String = when (Constant.API_SERVER) {
        Server.REAL -> Server.REAL.url
        Server.TEST -> Server.TEST.url
        Server.LOCAL -> Server.LOCAL.url
    } + "api/"

    @JvmStatic
    val INSTANCE: Module = module {
        single {
            RetrofitInterceptor()
        }
        single {
            createOkHttp(get())
        }
        single {
            createRetrofit(get(), get())
        }
        single {
            RetrofitUtil()
        }
    }

    @JvmStatic
    private fun createOkHttp(interceptor: RetrofitInterceptor): OkHttpClient = OkHttpClient.Builder()
        .cache(Cache(DirectoryFactory.CACHE_RETROFIT_DIR, CACHE_SIZE))
        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .addInterceptor(interceptor)
        .build()

    @JvmStatic
    private fun createRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(API_SERVER_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
}
