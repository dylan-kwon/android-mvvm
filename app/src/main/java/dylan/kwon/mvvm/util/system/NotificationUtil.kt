package dylan.kwon.mvvm.util.system

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import dylan.kwon.mvvm.R
import dylan.kwon.mvvm.util.LogUtil

class NotificationUtil(val applicationContext: Context) {

    companion object {
        @JvmField
        val TAG: String = NotificationUtil::class.java.simpleName
    }

    object Group {
        const val DEFAULT_ID: String = "NotificationUtil.Group.DEFAULT_ID"
    }

    object Channel {
        const val DEFAULT_ID: String = "NotificationUtil.Channel.DEFAULT_ID"
    }

    private val instance: NotificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    /**
     * 앱 기본 그룹과 채널을 하나씩 생성
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun init() {
        createChannelGroup(
            Group.DEFAULT_ID,
            applicationContext.getString(R.string.notification_default_group_name)
        )
        createChannel(
            Channel.DEFAULT_ID,
            applicationContext.getString(R.string.notification_default_channel_name),
            applicationContext.getString(R.string.notification_default_channel_description),
            groupId = Group.DEFAULT_ID
        )
    }

    /**
     * 노티피케이션 그룹 생성
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createChannelGroup(id: String, name: String): NotificationChannelGroup = NotificationChannelGroup(id, name).also {
        instance.createNotificationChannelGroup(it)
    }

    /**
     * 노티피케이션 채널 생성
     */
    @JvmOverloads
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createChannel(
        id: String,
        name: String,
        description: String,
        importance: Int = NotificationManager.IMPORTANCE_HIGH,
        lockScreenVisible: Int = Notification.VISIBILITY_PUBLIC,
        groupId: String
    ): NotificationChannel = NotificationChannel(id, name, importance).also {
        it.group = groupId
        it.description = description
        it.lockscreenVisibility = lockScreenVisible
        it.vibrationPattern = longArrayOf(1000, 1000)
        it.lightColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)
        instance.createNotificationChannel(it)
    }

    /**
     * 노티피케이션 객체를 만듬
     */
    fun createNotification(data: Data, intent: PendingIntent?): Notification {
        val notificationBuilder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(applicationContext, data.channelId)

        } else {
            Notification.Builder(applicationContext)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(longArrayOf(0, 1000))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLights(
                    ContextCompat.getColor(applicationContext, R.color.colorAccent),
                    1000,
                    1000
                )
        }
        return notificationBuilder
            .setContentTitle(data.contentTitle)
            .setContentText(data.contentText)
            .setTicker(data.contentText)
            .setAutoCancel(data.autoCancel)
            .setSmallIcon(data.smallIcon)
            .setCategory(data.category)
            .setVisibility(data.visibility)
            .setWhen(data.`when`)
            .setContentIntent(intent)
            .setDeleteIntent(createDismissPendingIntent(applicationContext))
            .setColor(ContextCompat.getColor(applicationContext, data.colorRes))
            .build()
    }

    fun createDismissPendingIntent(context: Context): PendingIntent {
        val intent: Intent = Intent(context, NotificationDismissReceiver::class.java)
        return PendingIntent.getBroadcast(context.applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    /**
     * 노티피케이션 객체를 만듬과 동시에 띄우기
     */
    fun notify(data: Data, intent: PendingIntent?) = notify(
        id = data.channelId.hashCode(),
        notification = createNotification(data, intent)
    )

    /**
     * 노티피케이션을 띄움
     */
    fun notify(id: Int, notification: Notification) {
        instance.notify(id, notification)
    }

    /**
     * Notification Dismiss Receiver
     */
    class NotificationDismissReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            LogUtil.i(TAG, "NotificationDismissReceiver.onReceive()")
        }
    }

    /**
     * 노티피케이션 데이터
     */
    data class Data(
        val channelId: String = Channel.DEFAULT_ID,
        val contentTitle: String,
        val contentText: String,
        val setTicker: String = contentText,
        val autoCancel: Boolean = true,
        val smallIcon: Int = R.mipmap.ic_launcher,
        val category: String = Notification.CATEGORY_MESSAGE,
        val visibility: Int = Notification.VISIBILITY_PUBLIC,
        val colorRes: Int = R.color.colorPrimary,
        val `when`: Long = System.currentTimeMillis()
    )

}
