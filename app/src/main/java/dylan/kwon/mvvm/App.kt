package dylan.kwon.mvvm

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import androidx.core.app.ActivityCompat
import dylan.kwon.mvvm.di.AppModule
import dylan.kwon.mvvm.util.LeakUtil
import dylan.kwon.mvvm.util.glide.GlideApp
import dylan.kwon.mvvm.util.system.NotificationUtil
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import kotlin.system.exitProcess


class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @JvmStatic
        fun exit(activity: Activity) {
            ActivityCompat.finishAffinity(activity)
            Process.killProcess(Process.myPid())
            exitProcess(0)
        }
    }

    private val notificationUtil: NotificationUtil by inject()

    override fun onCreate() {
        super.onCreate()
        initApp()
        initKoin()
        initNotification()
        LeakUtil.init()
    }

    private fun initApp() {
        context = this
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModule.getModules())
        }
    }

    private fun initNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationUtil.init()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        GlideApp.get(this)
            .clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        GlideApp.get(this)
            .trimMemory(level)
    }

}
