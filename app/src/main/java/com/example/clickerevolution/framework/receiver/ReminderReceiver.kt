package com.example.clickerevolution.framework.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.clickerevolution.framework.notification.NotificationHelper

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Notifications check", "ReminderReceiver: Received broadcast for notification")
        NotificationHelper.sendNotification(context)
    }
}