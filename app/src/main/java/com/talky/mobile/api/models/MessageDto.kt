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
 * @param id Uniq UUID of the message
 * @param friendshipId UUID of the friendship where the message was sended
 * @param author UUID of the user that made the request
 * @param content Text content of the message
 * @param createdAt Creation date of the message
 */

data class MessageDto (

    /* Uniq UUID of the message */
    @SerializedName("id")
    val id: java.util.UUID? = null,

    /* UUID of the friendship where the message was sended */
    @SerializedName("friendshipId")
    val friendshipId: java.util.UUID? = null,

    /* UUID of the user that made the request */
    @SerializedName("author")
    val author: java.util.UUID? = null,

    /* Text content of the message */
    @SerializedName("content")
    val content: kotlin.String? = null,

    /* Creation date of the message */
    @SerializedName("createdAt")
    val createdAt: java.time.OffsetDateTime? = null

)

