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

import com.talky.mobile.api.models.Sort

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param offset 
 * @param sort 
 * @param pageNumber 
 * @param pageSize 
 * @param paged 
 * @param unpaged 
 */

data class PageableObject (

    @SerializedName("offset")
    val offset: kotlin.Long? = null,

    @SerializedName("sort")
    val sort: Sort? = null,

    @SerializedName("pageNumber")
    val pageNumber: kotlin.Int? = null,

    @SerializedName("pageSize")
    val pageSize: kotlin.Int? = null,

    @SerializedName("paged")
    val paged: kotlin.Boolean? = null,

    @SerializedName("unpaged")
    val unpaged: kotlin.Boolean? = null

)

