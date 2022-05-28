package com.talky.mobile.api.apis

import com.talky.mobile.api.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import com.talky.mobile.api.models.AssetTemporaryLinkResponseDto
import com.talky.mobile.api.models.CreateUserRequestDto
import com.talky.mobile.api.models.UpdateUserRequestDto
import com.talky.mobile.api.models.UserDto

interface UserControllerApi {
    /**
     * 
     * Create a new user that matches the user that made the request.
     * Responses:
     *  - 200: OK
     *
     * @param createUserRequestDto 
     * @return [UserDto]
     */
    @POST("api/v1/users")
    suspend fun createUser(@Body createUserRequestDto: CreateUserRequestDto): Response<UserDto>

    /**
     * 
     * Generate an upload link for a profile picture. The link can be used by a form to upload a profile picture. The generated link is available for 30 minutes.
     * Responses:
     *  - 200: OK
     *
     * @param extension 
     * @return [AssetTemporaryLinkResponseDto]
     */
    @GET("api/v1/users/profile-picture/upload")
    suspend fun getAssetUploadLink(@Query("extension") extension: kotlin.String): Response<AssetTemporaryLinkResponseDto>

    /**
     * 
     * Get the current user profile
     * Responses:
     *  - 200: OK
     *
     * @return [UserDto]
     */
    @GET("api/v1/users/me")
    suspend fun getCurrentUser(): Response<UserDto>

    /**
     * 
     * Get the profile of a user
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [UserDto]
     */
    @GET("api/v1/users/{id}")
    suspend fun getUserById(@Path("id") id: java.util.UUID): Response<UserDto>

    /**
     * 
     * List users
     * Responses:
     *  - 200: OK
     *
     * @param page Zero-based page index (0..N) (optional, default to 0)
     * @param size The size of the page to be returned (optional, default to 20)
     * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
     * @return [kotlin.collections.List<UserDto>]
     */
    @GET("api/v1/users")
    suspend fun getUsers(@Query("page") page: kotlin.Int? = 0, @Query("size") size: kotlin.Int? = 20, @Query("sort") sort: kotlin.collections.List<kotlin.String>? = null): Response<kotlin.collections.List<UserDto>>

    /**
     * 
     * Update the last login of a user. Can also be used to register a device
     * Responses:
     *  - 200: OK
     *
     * @return [Unit]
     */
    @POST("api/v1/users/ping")
    suspend fun ping(): Response<Unit>

    /**
     * 
     * Update the current user
     * Responses:
     *  - 200: OK
     *
     * @param updateUserRequestDto 
     * @return [UserDto]
     */
    @PUT("api/v1/users/me")
    suspend fun updateProfile(@Body updateUserRequestDto: UpdateUserRequestDto): Response<UserDto>

}
