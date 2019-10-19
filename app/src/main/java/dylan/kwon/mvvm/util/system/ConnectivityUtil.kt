package dylan.kwon.mvvm.util.system

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityUtil(applicationContext: Context) {

    private val instance: ConnectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected: Boolean
        get() {
            return instance.activeNetworkInfo != null && instance.activeNetworkInfo.isConnected
        }

}
