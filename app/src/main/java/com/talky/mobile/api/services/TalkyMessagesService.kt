package com.talky.mobile.api.services

import com.talky.mobile.api.apis.MessageControllerApi
import com.talky.mobile.api.models.MessageDto
import com.talky.mobile.api.models.MessageRequestDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkyMessagesService @Inject constructor(
    private val messageControllerApi: MessageControllerApi
) {
    suspend fun createMessage(friendshipId: UUID, content: String) = withContext(Dispatchers.IO) {
        val request = MessageRequestDto(friendshipId, content)
        messageControllerApi.postMessage(request)
    }

    suspend fun populateMessagesAfter(friendshipId: UUID, date: OffsetDateTime): List<MessageDto> = withContext(Dispatchers.IO) {
        val response = messageControllerApi.listMessages(date = date, fetch = "AFTER", friendshipId = friendshipId)

        // Ignore API fails
        if (!response.isSuccessful) return@withContext listOf()

        val responseBody = response.body()

        return@withContext  responseBody!!.content!!.sortedBy { it.createdAt }
    }

    suspend fun populateMessagesBefore(friendshipId: UUID, date: OffsetDateTime): List<MessageDto> {
        val response = messageControllerApi.listMessages(date = date, fetch = "BEFORE", friendshipId = friendshipId)

        // Ignore API fails
        if (!response.isSuccessful) return listOf()

        val responseBody = response.body()

        return  responseBody!!.content!!.sortedBy { it.createdAt }
    }
}