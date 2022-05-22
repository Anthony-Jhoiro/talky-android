package com.talky.mobile.api.apis

import com.talky.mobile.api.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import com.talky.mobile.api.models.FriendDto

interface FriendshipControllerApi {
    /**
     * 
     * List all friends of the current user
     * Responses:
     *  - 200: OK
     *
     * @param page Zero-based page index (0..N) (optional, default to 0)
     * @param size The size of the page to be returned (optional, default to 20)
     * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
     * @return [kotlin.collections.List<FriendDto>]
     */
    @GET("api/v1/friends")
    suspend fun listFriends(@Query("page") page: kotlin.Int? = 0, @Query("size") size: kotlin.Int? = 20, @Query("sort") sort: kotlin.collections.List<kotlin.String>? = null): Response<kotlin.collections.List<FriendDto>>

}
