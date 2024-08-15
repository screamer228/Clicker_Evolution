package com.example.clickerevolution.data.repository.notification

interface NotificationRepository {

    fun scheduleNotification(delay: Long)

    fun cancelNotification()
}