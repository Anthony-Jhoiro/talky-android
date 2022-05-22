package com.talky.mobile.api.users.apis

import com.talky.mobile.api.users.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody

import com.talky.mobile.api.users.models.AssetTemporaryLinkResponseDto
import com.talky.mobile.api.users.models.CreateUserRequestDto
import com.talky.mobile.api.users.models.PageUserDto
import com.talky.mobile.api.users.models.UpdateUserRequestDto
import com.talky.mobile.api.users.models.UserDto

interface UserControllerApi {
    /**
     * 
     * Create a new user that matches the user that made the request.
     * Responses:
     *  - 200: OK
     *
     * @param createUserRequestDto 
     * @return [Call]<[UserDto]>
     */
    @POST("api/v1/users")
    fun createUser(@Body createUserRequestDto: CreateUserRequestDto): Call<UserDto>

    /**
     * 
     * Generate an upload link for a profile picture. The link can be used by a form to upload a profile picture. The generated link is available for 30 minutes.
     * Responses:
     *  - 200: OK
     *
     * @param extension 
     * @return [Call]<[AssetTemporaryLinkResponseDto]>
     */
    @GET("api/v1/users/profile-picture/upload")
    fun getAssetUploadLink(@Query("extension") extension: kotlin.String): Call<AssetTemporaryLinkResponseDto>

    /**
     * 
     * Get the current user profile
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[UserDto]>
     */
    @GET("api/v1/users/me")
    fun getCurrentUser(): Call<UserDto>

    /**
     * 
     * Get the profile of a user
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [Call]<[UserDto]>
     */
    @GET("api/v1/users/{id}")
    fun getUserById(@Path("id") id: java.util.UUID): Call<UserDto>

    /**
     * 
     * List users
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[PageUserDto]>
     */
    @GET("api/v1/users")
    fun getUsers(): Call<PageUserDto>

    /**
     * 
     * Update the last login of a user. Can also be used to register a device
     * Responses:
     *  - 200: OK
     *
     * @return [Call]<[Unit]>
     */
    @POST("api/v1/users/ping")
    fun ping(): Call<Unit>

    /**
     * 
     * Update the current user
     * Responses:
     *  - 200: OK
     *
     * @param updateUserRequestDto 
     * @return [Call]<[UserDto]>
     */
    @PUT("api/v1/users/me")
    fun updateProfile(@Body updateUserRequestDto: UpdateUserRequestDto): Call<UserDto>

}
