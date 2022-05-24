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

import com.talky.mobile.api.models.UserDto

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param id UUID of the friendship
 * @param sender 
 * @param recipient 
 * @param status Current status of the friend request
 * @param creationDate Creation datetime
 * @param lastUpdate Last update datetime
 */

data class FriendRequestDto (

    /* UUID of the friendship */
    @SerializedName("id")
    val id: java.util.UUID? = null,

    @SerializedName("sender")
    val sender: UserDto? = null,

    @SerializedName("recipient")
    val recipient: UserDto? = null,

    /* Current status of the friend request */
    @SerializedName("status")
    val status: FriendRequestDto.Status? = null,

    /* Creation datetime */
    @SerializedName("creationDate")
    val creationDate: java.time.OffsetDateTime? = null,

    /* Last update datetime */
    @SerializedName("lastUpdate")
    val lastUpdate: java.time.OffsetDateTime? = null

) {

    /**
     * Current status of the friend request
     *
     * Values: aCCEPTED,pENDING,dENIED
     */
    enum class Status(val value: kotlin.String) {
        @SerializedName(value = "ACCEPTED") aCCEPTED("ACCEPTED"),
        @SerializedName(value = "PENDING") pENDING("PENDING"),
        @SerializedName(value = "DENIED") dENIED("DENIED");
    }
}

