package com.talky.mobile.providers

import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseDeviceService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}