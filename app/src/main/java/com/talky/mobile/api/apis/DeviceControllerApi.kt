package com.talky.mobile.api.apis

import com.talky.mobile.api.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import com.talky.mobile.api.models.DeviceDto

interface DeviceControllerApi {
    /**
     * 
     * Get all devices ids attached to a specific user
     * Responses:
     *  - 200: OK
     *
     * @param userId 
     * @return [kotlin.collections.Set<DeviceDto>]
     */
    @GET("api/v1/devices/user/{userId}")
    suspend fun getUserDevices(@Path("userId") userId: java.util.UUID): Response<kotlin.collections.Set<DeviceDto>>

}
