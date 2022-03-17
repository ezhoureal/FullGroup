package edu.umich.fullgroup.perminit_frontend

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.core.app.NotificationCompat

const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
const val NOTIFICATION_ID_FROM_RECEIVER= 1

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notification : Notification = NotificationCompat.Builder(
            context, context.getString(R.string.event_notification_channel_id))
                .setSmallIcon(R.drawable.ic_baseline_person_24)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID_FROM_RECEIVER, notification)
    }
}