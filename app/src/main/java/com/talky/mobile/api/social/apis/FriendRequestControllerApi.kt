package com.talky.mobile.api.social.apis

import com.talky.mobile.api.social.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody

import com.talky.mobile.api.social.models.CreateFriendRequestRequestDto
import com.talky.mobile.api.social.models.FriendRequestDto
import com.talky.mobile.api.social.models.UpdateFriendRequestRequestDto

interface FriendRequestControllerApi {
    /**
     * 
     * Create a friend request
     * Responses:
     *  - 200: OK
     *
     * @param createFriendRequestRequestDto 
     * @return [Call]<[FriendRequestDto]>
     */
    @POST("api/v1/requests")
    fun createFriendRequest(@Body createFriendRequestRequestDto: CreateFriendRequestRequestDto): Call<FriendRequestDto>

    /**
     * 
     * List friend requests of the current user
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[kotlin.collections.Set<FriendRequestDto>]>
     */
    @GET("api/v1/requests")
    fun listFriendRequests(): Call<kotlin.collections.Set<FriendRequestDto>>

    /**
     * 
     * Change the status of a friend request (ACCEPTED / DENIED)
     * Responses:
     *  - 200: OK
     *
     * @param requestId 
     * @param updateFriendRequestRequestDto 
     * @return [Call]<[Unit]>
     */
    @PUT("api/v1/requests/{requestId}")
    fun updateFriendRequest(@Path("requestId") requestId: java.util.UUID, @Body updateFriendRequestRequestDto: UpdateFriendRequestRequestDto): Call<Unit>

}
