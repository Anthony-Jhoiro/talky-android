/**
 * Post API
 *
 * Documentation of Post API
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
 * @param id Uniq identifier of the post
 * @param author 
 * @param createdAt Post creation date
 * @param content Post text content
 * @param privacy Post privacy (public / private)
 * @param assets List of assets links, these are temporary link, only available during a certain period of time.
 */

data class PostDto (

    /* Uniq identifier of the post */
    @SerializedName("id")
    val id: java.util.UUID? = null,

    @SerializedName("author")
    val author: UserDto? = null,

    /* Post creation date */
    @SerializedName("createdAt")
    val createdAt: java.time.OffsetDateTime? = null,

    /* Post text content */
    @SerializedName("content")
    val content: kotlin.String? = null,

    /* Post privacy (public / private) */
    @SerializedName("privacy")
    val privacy: PostDto.Privacy? = null,

    /* List of assets links, these are temporary link, only available during a certain period of time. */
    @SerializedName("assets")
    val assets: kotlin.collections.List<kotlin.String>? = null

) {

    /**
     * Post privacy (public / private)
     *
     * Values: pUBLIC,pRIVATE
     */
    enum class Privacy(val value: kotlin.String) {
        @SerializedName(value = "PUBLIC") pUBLIC("PUBLIC"),
        @SerializedName(value = "PRIVATE") pRIVATE("PRIVATE");
    }
}
