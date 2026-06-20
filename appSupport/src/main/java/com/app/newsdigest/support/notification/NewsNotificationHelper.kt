package com.app.newsdigest.support.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.app.newsdigest.log.Logger
import com.app.newsdigest.support.R

open class NewsNotificationHelper(
    private val context: Context,
) {

    fun ensureChannelCreated() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notification_channel_new_articles_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = context.getString(R.string.notification_channel_new_articles_description)
        }
        context.getSystemService(NotificationManager::class.java)
            ?.createNotificationChannel(channel)
    }

    open fun showNewArticlesNotification(newArticleCount: Int) {
        if (newArticleCount <= 0) {
            return
        }
        if (!areNotificationsEnabled()) {
            Logger.w(
                TAG,
                "Skipping new-articles notification — permission denied or notifications disabled"
            )
            return
        }
        ensureChannelCreated()
        val body = if (newArticleCount == 1) {
            context.getString(R.string.notification_new_articles_body_singular)
        } else {
            context.getString(R.string.notification_new_articles_body_plural, newArticleCount)
        }
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(context.applicationInfo.icon)
            .setContentTitle(context.getString(R.string.notification_new_articles_title))
            .setContentText(body)
            .setContentIntent(articleListPendingIntent())
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    fun areNotificationsEnabled(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    private fun articleListPendingIntent(): PendingIntent {
        val intent = Intent().apply {
            setClassName(context, MAIN_ACTIVITY_CLASS)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_NAV_DESTINATION, DESTINATION_ARTICLE_LIST)
        }
        return PendingIntent.getActivity(
            context,
            REQUEST_CODE_ARTICLE_LIST,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private companion object {
        private const val TAG = "NewsNotificationHelper"
        const val CHANNEL_ID = "new_articles"
        private const val NOTIFICATION_ID = 1001
        private const val REQUEST_CODE_ARTICLE_LIST = 1001
        private const val MAIN_ACTIVITY_CLASS = "com.app.newsdigest.MainActivity"
        private const val EXTRA_NAV_DESTINATION = "com.app.newsdigest.extra.NAV_DESTINATION"
        private const val DESTINATION_ARTICLE_LIST = "article_list"
    }
}
