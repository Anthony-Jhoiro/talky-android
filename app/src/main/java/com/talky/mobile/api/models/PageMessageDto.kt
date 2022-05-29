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

import com.talky.mobile.api.models.MessageDto
import com.talky.mobile.api.models.PageableObject
import com.talky.mobile.api.models.Sort

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param totalElements 
 * @param totalPages 
 * @param propertySize 
 * @param content 
 * @param number 
 * @param sort 
 * @param numberOfElements 
 * @param pageable 
 * @param first 
 * @param last 
 * @param empty 
 */

data class PageMessageDto (

    @SerializedName("totalElements")
    val totalElements: kotlin.Long? = null,

    @SerializedName("totalPages")
    val totalPages: kotlin.Int? = null,

    @SerializedName("size")
    val propertySize: kotlin.Int? = null,

    @SerializedName("content")
    val content: kotlin.collections.List<MessageDto>? = null,

    @SerializedName("number")
    val number: kotlin.Int? = null,

    @SerializedName("sort")
    val sort: Sort? = null,

    @SerializedName("numberOfElements")
    val numberOfElements: kotlin.Int? = null,

    @SerializedName("pageable")
    val pageable: PageableObject? = null,

    @SerializedName("first")
    val first: kotlin.Boolean? = null,

    @SerializedName("last")
    val last: kotlin.Boolean? = null,

    @SerializedName("empty")
    val empty: kotlin.Boolean? = null

)

