package com.talky.mobile.api.users.apis

import com.talky.mobile.api.users.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody

import com.talky.mobile.api.users.models.DeviceDto

interface DeviceControllerApi {
    /**
     * 
     * Get all devices ids attached to a specific user
     * Responses:
     *  - 200: OK
     *
     * @param userId 
     * @return [Call]<[kotlin.collections.Set<DeviceDto>]>
     */
    @GET("api/v1/devices/user/{userId}")
    fun getUserDevices(@Path("userId") userId: java.util.UUID): Call<kotlin.collections.Set<DeviceDto>>

}
