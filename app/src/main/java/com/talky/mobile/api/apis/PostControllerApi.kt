package com.talky.mobile.api.apis

import com.talky.mobile.api.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import com.talky.mobile.api.models.AssetTemporaryLinkResponseDto
import com.talky.mobile.api.models.CreatePostRequestDto
import com.talky.mobile.api.models.PostDto

interface PostControllerApi {
    /**
     * 
     * Create a post
     * Responses:
     *  - 200: OK
     *
     * @param createPostRequestDto 
     * @return [PostDto]
     */
    @POST("api/v1/posts")
    suspend fun createPost(@Body createPostRequestDto: CreatePostRequestDto): Response<PostDto>

    /**
     * 
     * Generate an upload link for an asset. The link can be used by a form to upload an asset. The generated link is available for 30 minutes.
     * Responses:
     *  - 200: OK
     *
     * @param extension 
     * @return [AssetTemporaryLinkResponseDto]
     */
    @GET("api/v1/posts/asset/upload")
    suspend fun getAssetUploadLink(@Query("extension") extension: kotlin.String): Response<AssetTemporaryLinkResponseDto>

    /**
     * 
     * List public posts
     * Responses:
     *  - 200: OK
     *
     * @param page Zero-based page index (0..N) (optional, default to 0)
     * @param size The size of the page to be returned (optional, default to 20)
     * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
     * @return [kotlin.collections.List<PostDto>]
     */
    @GET("api/v1/posts")
    suspend fun listPosts(@Query("page") page: kotlin.Int? = 0, @Query("size") size: kotlin.Int? = 20, @Query("sort") sort: kotlin.collections.List<kotlin.String>? = null): Response<kotlin.collections.List<PostDto>>

    /**
     * 
     * List posts from a user
     * Responses:
     *  - 200: OK
     *
     * @param authorId UUID of the posts author
     * @param page Zero-based page index (0..N) (optional, default to 0)
     * @param size The size of the page to be returned (optional, default to 20)
     * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
     * @return [kotlin.collections.List<PostDto>]
     */
    @GET("api/v1/posts/author/{authorId}")
    suspend fun listUserPosts(@Path("authorId") authorId: java.util.UUID, @Query("page") page: kotlin.Int? = 0, @Query("size") size: kotlin.Int? = 20, @Query("sort") sort: kotlin.collections.List<kotlin.String>? = null): Response<kotlin.collections.List<PostDto>>

}
