package dylan.kwon.mvvm.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import dylan.kwon.mvvm.util.extension.putBoolean

interface AppPreference {
    var isFirstInit: Boolean
}

class AppPreferenceImpl(applicationContext: Context) : AppPreference {

    companion object {
        const val PREF_NAME: String = "app_pref"
    }

    object Key {
        const val IS_FIRST_INIT: String = "AppPreferenceImpl.Key.IS_FIRST_INIT"
    }

    private val instance: SharedPreferences =
        applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override var isFirstInit: Boolean
        set(value) {
            instance.putBoolean(Key.IS_FIRST_INIT, value)
        }
        get() = instance.getBoolean(Key.IS_FIRST_INIT, true)

}