package com.example.daggerseminar

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import kotlin.random.Random

/**
 * @author a.s.korchagin
 */
class NotificationDelegate(
    private val context: Context,
){

    fun createNotification(message: String): Notification {
        // Создаем уведомление с помощью NotificationCompat.Builder
        return NotificationCompat.Builder(context, App.CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            // .setContentTitle(title) // Устанавливаем заголовок уведомления
            .setContentText(message) // Устанавливаем текст уведомления
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Устанавливаем маленькую иконку уведомления
            .setOngoing(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    UPDATE_CURRENT_FLAGS
                )
            )
            .build()
    }

    fun showNotification(notification: Notification) {
        // Получаем экземпляр NotificationManager из контекста
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        // Отображаем уведомление с помощью NotificationManager
        notificationManager.notify(/* Уникальный идентификатор уведомления */ Random.nextInt(),
            notification
        )
    }
}
