/**
 * Users API
 *
 * Documentation of Users API
 *
 * The version of the OpenAPI document: v1
 * 
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.talky.mobile.api.models


import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param deviceId If of the user device
 * @param user If of the user
 */

data class DeviceDto (

    /* If of the user device */
    @SerializedName("deviceId")
    val deviceId: kotlin.String? = null,

    /* If of the user */
    @SerializedName("user")
    val user: java.util.UUID? = null

)

