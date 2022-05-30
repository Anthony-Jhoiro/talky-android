package com.talky.mobile.api.services

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.talky.mobile.api.apis.UserControllerApi
import com.talky.mobile.api.models.UserPingDto
import dagger.hilt.EntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PingService @Inject constructor(
    private val userControllerApi: UserControllerApi
    ) {
    suspend fun sendPingWithDevice() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result

            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                sendPingWithDevice(token)
            }

        })
    }

    private suspend fun sendPingWithDevice(token: String) {
        val request = UserPingDto(token)
        userControllerApi.ping(request)
    }

}