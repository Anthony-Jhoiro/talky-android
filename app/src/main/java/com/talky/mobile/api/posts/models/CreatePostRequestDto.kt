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

package com.talky.mobile.api.posts.models


import com.squareup.moshi.Json

/**
 * 
 *
 * @param content Text content
 * @param privacy Privacy of the post (PUBLIC: Any user can see it, PRIVATE: Only author and friends can see it)
 * @param assets List of assets ids (remember to add the extention)
 */

data class CreatePostRequestDto (

    /* Text content */
    @Json(name = "content")
    val content: kotlin.String? = null,

    /* Privacy of the post (PUBLIC: Any user can see it, PRIVATE: Only author and friends can see it) */
    @Json(name = "privacy")
    val privacy: CreatePostRequestDto.Privacy? = null,

    /* List of assets ids (remember to add the extention) */
    @Json(name = "assets")
    val assets: kotlin.collections.List<kotlin.String>? = null

) {

    /**
     * Privacy of the post (PUBLIC: Any user can see it, PRIVATE: Only author and friends can see it)
     *
     * Values: pUBLIC,pRIVATE
     */
    enum class Privacy(val value: kotlin.String) {
        @Json(name = "PUBLIC") pUBLIC("PUBLIC"),
        @Json(name = "PRIVATE") pRIVATE("PRIVATE");
    }
}

