package com.talky.mobile.facades

import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseDeviceServiceFacade : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}