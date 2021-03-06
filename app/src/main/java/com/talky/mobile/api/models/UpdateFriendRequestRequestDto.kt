/**
 * Social API
 *
 * Documentation of Social API
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
 * @param status New status of the request (ACCEPTED / DENIED / PENDING)
 */

data class UpdateFriendRequestRequestDto (

    /* New status of the request (ACCEPTED / DENIED / PENDING) */
    @SerializedName("status")
    val status: UpdateFriendRequestRequestDto.Status? = null

) {

    /**
     * New status of the request (ACCEPTED / DENIED / PENDING)
     *
     * Values: aCCEPTED,pENDING,dENIED
     */
    enum class Status(val value: kotlin.String) {
        @SerializedName(value = "ACCEPTED") aCCEPTED("ACCEPTED"),
        @SerializedName(value = "PENDING") pENDING("PENDING"),
        @SerializedName(value = "DENIED") dENIED("DENIED");
    }
}

