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
 * @param friends List of friends in the friendship
 * @param creationDate Creation datetime
 */

data class FriendshipDto (

    /* UUID of the friendship */
    @SerializedName("id")
    val id: java.util.UUID? = null,

    /* List of friends in the friendship */
    @SerializedName("friends")
    val friends: kotlin.collections.List<UserDto>? = null,

    /* Creation datetime */
    @SerializedName("creationDate")
    val creationDate: java.time.OffsetDateTime? = null

)

