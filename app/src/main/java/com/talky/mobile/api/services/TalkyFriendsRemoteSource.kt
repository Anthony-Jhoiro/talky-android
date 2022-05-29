package com.talky.mobile.api.services

import com.talky.mobile.api.apis.FriendRequestControllerApi
import com.talky.mobile.api.apis.FriendshipControllerApi
import com.talky.mobile.api.models.CreateFriendRequestRequestDto
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.api.models.FriendshipDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkyFriendsRemoteSource @Inject constructor(
    private val friendRequestApi: FriendRequestControllerApi,
    private val friendshipControllerApi: FriendshipControllerApi
) {

    suspend fun createFriendRequest(request: CreateFriendRequestRequestDto): FriendRequestDto? =
        withContext(Dispatchers.IO) {

            val response = friendRequestApi.createFriendRequest(request)
            return@withContext response.body()
        }

    suspend fun getFriendship(friendshipId: UUID): FriendshipDto? = withContext(Dispatchers.IO) {
        val response = friendshipControllerApi.getFriendshipById(friendshipId)
        return@withContext response.body()
    }
}