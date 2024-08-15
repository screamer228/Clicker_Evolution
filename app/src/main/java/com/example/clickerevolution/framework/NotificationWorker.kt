package com.example.clickerevolution.framework

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.clickerevolution.framework.notification.NotificationHelper

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        NotificationHelper.sendNotification(applicationContext)
        return Result.success()
    }
}