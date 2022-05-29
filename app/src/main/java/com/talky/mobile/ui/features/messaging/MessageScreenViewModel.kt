package com.talky.mobile.ui.features.messaging

import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talky.mobile.api.MessagesRemoteSource
import com.talky.mobile.api.NotificationController
import com.talky.mobile.api.TalkyFriendsRemoteSource
import com.talky.mobile.api.models.FriendshipDto
import com.talky.mobile.api.models.MessageDto
import com.talky.mobile.ui.NavigationKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MessageScreenViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val messagesRemoteSource: MessagesRemoteSource,
    private val talkyFriendsRemoteSource: TalkyFriendsRemoteSource,
    private val notificationController: NotificationController

) :
    ViewModel() {

    val toast = notificationController.notificationReceiver

    private val friendshipId = UUID.fromString(
        stateHandle.get<String>(
            NavigationKeys.Arg.FRIENDSHIP_ID
        )
    )

    var friendship: FriendshipDto? = null

    private val _messages = mutableStateListOf<MessageDto>()
    val messages: List<MessageDto>
        get() = _messages


    init {
        viewModelScope.launch {
            friendship = talkyFriendsRemoteSource.getFriendship(friendshipId)
            val messages =
                messagesRemoteSource.populateMessagesBefore(friendshipId, OffsetDateTime.now())
            populateMessages(messages)
            notificationController.notificationReceiver.collect {
                println(it.data)
            }

        }
    }

    private fun populateMessages(newMessages: List<MessageDto>) {
        this._messages.addAll(
            newMessages.filter { newMessage ->
                this._messages.find { it.id == newMessage.id } == null
            }
        )
    }


    fun postMessage(content: String) {
        viewModelScope.launch {
            val response = messagesRemoteSource.createMessage(
                friendshipId, content
            )

            val message = response.body()
            if (message != null) {
                _messages.add(message)
            }
        }
    }


}