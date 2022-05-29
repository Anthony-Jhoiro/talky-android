package com.talky.mobile.api

import com.talky.mobile.api.apis.FriendRequestControllerApi
import com.talky.mobile.api.models.CreateFriendRequestRequestDto
import com.talky.mobile.api.models.FriendRequestDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkyFriendsRemoteSource @Inject constructor(private val friendRequestApi: FriendRequestControllerApi) {

    suspend fun createFriendRequest(request: CreateFriendRequestRequestDto): FriendRequestDto? = withContext(Dispatchers.IO) {

        val response = friendRequestApi.createFriendRequest(request)

        println(response)

        return@withContext response.body();
    }
}