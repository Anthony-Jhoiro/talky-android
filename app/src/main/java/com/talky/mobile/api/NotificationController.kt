package com.talky.mobile.api

import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationController @Inject constructor() {

    private val notification = MutableSharedFlow<RemoteMessage>()
    val notificationReceiver = notification.asSharedFlow()


    fun onMessageReceived(message: RemoteMessage) {
        notification.tryEmit(message)
    }
}