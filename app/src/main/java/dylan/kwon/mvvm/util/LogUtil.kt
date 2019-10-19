package dylan.kwon.mvvm.util

import android.util.Log
import dylan.kwon.mvvm.Constant
import dylan.kwon.mvvm.util.extension.DATE_TIME_FORMAT
import dylan.kwon.mvvm.util.extension.format
import java.util.*

object LogUtil {

    @JvmStatic
    fun v(tag: String? = null, message: String) {
        if (!Constant.IS_LOG_MODE) {
            return
        }
        printLongLog(tag, message, Log.VERBOSE)
    }

    @JvmStatic
    fun i(tag: String? = null, message: String) {
        if (!Constant.IS_LOG_MODE) {
            return
        }
        printLongLog(tag, message, Log.INFO)
    }

    @JvmStatic
    fun d(tag: String? = null, message: String) {
        if (!Constant.IS_LOG_MODE) {
            return
        }
        printLongLog(tag, message, Log.DEBUG)
    }

    @JvmStatic
    fun w(tag: String? = null, message: String) {
        if (!Constant.IS_LOG_MODE) {
            return
        }
        printLongLog(tag, message, Log.WARN)
    }

    @JvmStatic
    fun e(tag: String? = null, message: String) {
        if (!Constant.IS_LOG_MODE) {
            return
        }
        printLongLog(tag, message, Log.ERROR)
    }

    @JvmStatic
    private fun printLongLog(tag: String? = null, message: String, logLevel: Int) {
        val nonNullTag: String = tag ?: Constant.TAG

        val maxLogSize = 4000
        val formatMessage: String = createMessage(message)

        for (i: Int in 0..(formatMessage.length / maxLogSize)) {
            val start: Int = i * maxLogSize
            val end: Int = ((i + 1) * maxLogSize).let {
                if (it > formatMessage.length) formatMessage.length else it
            }
            val cutMessage: String = formatMessage.substring(start, end)
            when (logLevel) {
                Log.VERBOSE -> Log.v(nonNullTag, cutMessage)
                Log.INFO -> Log.i(nonNullTag, cutMessage)
                Log.DEBUG -> Log.d(nonNullTag, cutMessage)
                Log.WARN -> Log.w(nonNullTag, cutMessage)
                Log.ERROR -> Log.e(nonNullTag, cutMessage)
            }
        }
    }

    @JvmStatic
    private fun createMessage(message: String): String {
        val thread: String = Thread.currentThread().name
        val dateTime: String = Date() format DATE_TIME_FORMAT
        return "$dateTime [$thread] $message"
    }

}