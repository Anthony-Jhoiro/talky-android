package com.talky.mobile.api

import com.talky.mobile.api.apis.FriendRequestControllerApi
import com.talky.mobile.api.apis.UserControllerApi
import com.talky.mobile.api.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton




@Singleton
class TalkyFriendRequestListRemoteSource @Inject constructor(private val friendRequestControllerApi: FriendRequestControllerApi) {

    private var friendRequestsList: List<FriendRequestDto>? = null

    suspend fun getFriendRequests(): List<FriendRequestDto> = withContext(Dispatchers.IO) {
        if (friendRequestsList != null) {
            return@withContext friendRequestsList!!
        }
        val friendList = friendRequestControllerApi.listFriendRequests().body()!!.toList()
        this@TalkyFriendRequestListRemoteSource.friendRequestsList = friendList
        return@withContext friendList
    }

    suspend fun changeFriendRequestStatus(friendRequestDto: FriendRequestDto, status: FriendRequestDto.Status): Boolean {
        val request = UpdateFriendRequestRequestDto(UpdateFriendRequestRequestDto.Status.valueOf(status.name))
        val response = friendRequestControllerApi.updateFriendRequest(friendRequestDto.id!!, request)

        return response.isSuccessful
    }

    fun reset() {
        this.friendRequestsList = null
    }
}
