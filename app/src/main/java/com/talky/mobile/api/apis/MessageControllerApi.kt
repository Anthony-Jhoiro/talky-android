package com.talky.mobile.api.apis

import com.talky.mobile.api.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import com.talky.mobile.api.models.MessageDto
import com.talky.mobile.api.models.MessageRequestDto
import com.talky.mobile.api.models.PageMessageDto

interface MessageControllerApi {
    /**
     * 
     * List messages for a specific friendship
     * Responses:
     *  - 200: OK
     *
     * @param friendshipId 
     * @param fetch  (optional, default to AFTER)
     * @param date  (optional)
     * @param limit  (optional, default to 20)
     * @return [PageMessageDto]
     */
    @GET("api/v1/messages/friendship/{friendshipId}")
    suspend fun listMessages(@Path("friendshipId") friendshipId: java.util.UUID, @Query("fetch") fetch: kotlin.String? = AFTER, @Query("date") date: java.time.OffsetDateTime? = null, @Query("limit") limit: kotlin.Int? = 20): Response<PageMessageDto>

    /**
     * 
     * Send a message, if the reciepient has registered a device, he will recieved a push notification.
     * Responses:
     *  - 200: OK
     *
     * @param messageRequestDto 
     * @return [MessageDto]
     */
    @POST("api/v1/messages")
    suspend fun postMessage(@Body messageRequestDto: MessageRequestDto): Response<MessageDto>

}
