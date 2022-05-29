package com.talky.mobile.ui.features.messaging

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talky.mobile.api.services.TalkyMessagesService
import com.talky.mobile.api.services.TalkyFriendsService
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
    private val talkyMessagesService: TalkyMessagesService,
    private val talkyFriendsService: TalkyFriendsService

) :
    ViewModel() {

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
            friendship = talkyFriendsService.getFriendship(friendshipId)
            val messages =
                talkyMessagesService.populateMessagesBefore(friendshipId, OffsetDateTime.now())
            populateMessages(messages)
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
            val response = talkyMessagesService.createMessage(
                friendshipId, content
            )

            val message = response.body()
            if (message != null) {
                _messages.add(message)
            }
        }
    }


}