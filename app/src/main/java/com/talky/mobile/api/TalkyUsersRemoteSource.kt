package com.talky.mobile.api

import com.talky.mobile.api.apis.UserControllerApi
import com.talky.mobile.api.models.CreateUserRequestDto
import com.talky.mobile.api.models.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkyUsersRemoteSource @Inject constructor(private val userApi: UserControllerApi) {

    private var currentUserProfile: UserDto? = null

    suspend fun getProfile(): UserDto? = withContext(Dispatchers.IO) {
        if (currentUserProfile != null) {
            return@withContext currentUserProfile
        }

        this@TalkyUsersRemoteSource.currentUserProfile = userApi.getCurrentUser().body()
        return@withContext currentUserProfile
    }

    suspend fun createProfile(request: CreateUserRequestDto): UserDto? = withContext(Dispatchers.IO) {
        if (currentUserProfile != null) {
            return@withContext currentUserProfile
        }

        val response = userApi.createUser(request)

        println(response)

        this@TalkyUsersRemoteSource.currentUserProfile = response.body()
        return@withContext currentUserProfile
    }

    fun reset() {
        this.currentUserProfile = null
    }
}