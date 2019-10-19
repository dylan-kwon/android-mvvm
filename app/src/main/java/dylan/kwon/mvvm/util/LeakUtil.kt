package dylan.kwon.mvvm.util

import dylan.kwon.mvvm.Constant
import leakcanary.AppWatcher

object LeakUtil {

    const val IS_WATCH_ACTIVITIES: Boolean = true

    const val IS_WATCH_FRAGMENT_VIES: Boolean = true

    const val WATCH_DURATION_MILLIS: Long = 3_000L

    @JvmStatic
    fun init() {
        AppWatcher.config.copy(
            enabled = Constant.IS_TEST_MODE,
            watchActivities = IS_WATCH_ACTIVITIES,
            watchFragmentViews = IS_WATCH_FRAGMENT_VIES,
            watchDurationMillis = WATCH_DURATION_MILLIS
        )
    }

    @JvmStatic
    fun watchLeak(instance: Any) {
        AppWatcher.objectWatcher.watch(instance)
    }

}