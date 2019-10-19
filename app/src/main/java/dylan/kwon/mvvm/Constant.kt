package dylan.kwon.mvvm

import dylan.kwon.mvvm.di.NetworkModule.Server

class Constant {

    companion object {

        @JvmField
        val TAG: String = App.context.getString(R.string.app_name)

        @JvmStatic
        val API_SERVER: Server = Server.TEST

        const val IS_LOG_MODE: Boolean = true
        const val IS_TEST_MODE: Boolean = true

    }

}