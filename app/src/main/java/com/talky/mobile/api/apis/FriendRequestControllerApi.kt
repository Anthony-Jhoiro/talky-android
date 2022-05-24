package com.talky.mobile.api.apis

import com.talky.mobile.api.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import com.talky.mobile.api.models.CreateFriendRequestRequestDto
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.api.models.UpdateFriendRequestRequestDto

interface FriendRequestControllerApi {
    /**
     * 
     * Create a friend request
     * Responses:
     *  - 200: OK
     *
     * @param createFriendRequestRequestDto 
     * @return [FriendRequestDto]
     */
    @POST("api/v1/requests")
    suspend fun createFriendRequest(@Body createFriendRequestRequestDto: CreateFriendRequestRequestDto): Response<FriendRequestDto>

    /**
     * 
     * List friend requests of the current user
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.collections.Set<FriendRequestDto>]
     */
    @GET("api/v1/requests")
    suspend fun listFriendRequests(): Response<kotlin.collections.Set<FriendRequestDto>>

    /**
     * 
     * Change the status of a friend request (ACCEPTED / DENIED)
     * Responses:
     *  - 200: OK
     *
     * @param requestId 
     * @param updateFriendRequestRequestDto 
     * @return [Unit]
     */
    @PUT("api/v1/requests/{requestId}")
    suspend fun updateFriendRequest(@Path("requestId") requestId: java.util.UUID, @Body updateFriendRequestRequestDto: UpdateFriendRequestRequestDto): Response<Unit>

}
