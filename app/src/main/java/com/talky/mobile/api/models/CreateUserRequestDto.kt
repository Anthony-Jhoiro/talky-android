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

package com.talky.mobile.api.models


import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param displayedName 
 * @param profilePicture 
 * @param defaultProfilePicture 
 */

data class CreateUserRequestDto (

    @SerializedName("displayedName")
    val displayedName: kotlin.String? = null,

    @SerializedName("profilePicture")
    val profilePicture: kotlin.String? = null,

    @SerializedName("defaultProfilePicture")
    val defaultProfilePicture: kotlin.String? = null

)
