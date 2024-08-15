package com.example.clickerevolution.data.repository.notification

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.clickerevolution.framework.NotificationWorker
import java.util.concurrent.TimeUnit

class NotificationRepositoryImpl(
    private val context: Context
) : NotificationRepository {

    override fun scheduleNotification(delay: Long) {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    override fun cancelNotification() {
        WorkManager.getInstance(context).cancelAllWork()
    }
}