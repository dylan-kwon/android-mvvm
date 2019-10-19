package dylan.kwon.mvvm.util.system

import android.app.ActivityManager
import android.content.Context


class ActivityUtil(val applicationContext: Context) {

    private val instance: ActivityManager =
        applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    /**
     * 앱이 Foreground 상태인지 체크
     *
     * @return true: foreground, false: background
     */
    val isForeground: Boolean
        get() {
            for (processInfo: ActivityManager.RunningAppProcessInfo in instance.runningAppProcesses) {
                if (processInfo.processName != applicationContext.packageName) {
                    continue
                }
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true
                }
            }
            return false
        }

}
