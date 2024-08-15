package com.example.clickerevolution.framework.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.clickerevolution.R
import com.example.clickerevolution.presentation.host.HostActivity

object NotificationHelper {

    fun sendNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "reminder_channel",
            "Reminder Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        // Создайте Intent для открытия Activity
        val intent = Intent(context, HostActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "reminder_channel")
            .setContentTitle("Пора собрать заработанное!")
            .setContentText("Ваш оффлайн сбор остановился. Зайдите, чтобы снова запустить его!")
            .setSmallIcon(R.drawable.ic_coin)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)  // Устанавливаем PendingIntent
            .build()

        notificationManager.notify(1, notification)
    }
}