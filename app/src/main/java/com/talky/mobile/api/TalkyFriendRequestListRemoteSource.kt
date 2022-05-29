package com.talky.mobile.api

import com.talky.mobile.api.apis.FriendRequestControllerApi
import com.talky.mobile.api.apis.UserControllerApi
import com.talky.mobile.api.models.CreateUserRequestDto
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.api.models.UpdateUserRequestDto
import com.talky.mobile.api.models.UserDto
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

        println(friendList)

        this@TalkyFriendRequestListRemoteSource.friendRequestsList = friendList

        return@withContext friendList
    }

    fun reset() {
        this.friendRequestsList = null
    }
}
