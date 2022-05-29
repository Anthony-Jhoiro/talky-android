package com.talky.mobile.providers

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.talky.mobile.api.INotificationController
import dagger.hilt.EntryPoints

class FirebaseDeviceService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        println("toto : " + message.rawData.toString())
        val notificationController = EntryPoints.get(applicationContext, INotificationController::class.java).getNotificationController()
        println("tata : " + message.rawData.toString())

        notificationController.onMessageReceived(message)
    }
}