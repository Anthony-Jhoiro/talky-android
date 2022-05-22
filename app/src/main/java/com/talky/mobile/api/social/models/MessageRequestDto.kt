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

package com.talky.mobile.api.social.models


import com.squareup.moshi.Json

/**
 * 
 *
 * @param friendshipId UUID of the friendship where the message should be sent
 * @param content Text content of the message
 */

data class MessageRequestDto (

    /* UUID of the friendship where the message should be sent */
    @Json(name = "friendshipId")
    val friendshipId: java.util.UUID? = null,

    /* Text content of the message */
    @Json(name = "content")
    val content: kotlin.String? = null

)

