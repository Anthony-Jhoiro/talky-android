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

package com.talky.mobile.api.users.models

import com.talky.mobile.api.users.models.Sort

import com.squareup.moshi.Json

/**
 * 
 *
 * @param offset 
 * @param sort 
 * @param paged 
 * @param unpaged 
 * @param pageNumber 
 * @param pageSize 
 */

data class PageableObject (

    @Json(name = "offset")
    val offset: kotlin.Long? = null,

    @Json(name = "sort")
    val sort: Sort? = null,

    @Json(name = "paged")
    val paged: kotlin.Boolean? = null,

    @Json(name = "unpaged")
    val unpaged: kotlin.Boolean? = null,

    @Json(name = "pageNumber")
    val pageNumber: kotlin.Int? = null,

    @Json(name = "pageSize")
    val pageSize: kotlin.Int? = null

)

