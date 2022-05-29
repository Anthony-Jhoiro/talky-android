package com.talky.mobile.api

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import com.talky.mobile.api.apis.PostControllerApi
import com.talky.mobile.api.clients.UploadApi
import com.talky.mobile.api.models.CreatePostRequestDto
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.ui.commons.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Part
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TalkyAssetsRemoteSource @Inject constructor(
    private val postApi: PostControllerApi,
    private val uploadApi: UploadApi) {

    suspend fun uploadAsset(asset: Bitmap): String? = withContext(Dispatchers.IO) {
        val extension = "png"

        val uploadLinkResponse = postApi.getAssetUploadLink(extension)

        if (!uploadLinkResponse.isSuccessful) {
            return@withContext null
        }

        val uploadUrl = uploadLinkResponse.body()!!.url!!

        val bos = ByteArrayOutputStream()
        asset.compress(CompressFormat.PNG, 90, bos)
        val bitmapData: ByteArray = bos.toByteArray()

        val reqFile: RequestBody =
            bitmapData.toRequestBody("application/octet-stream".toMediaTypeOrNull(), 0)

        val assetId = uploadUrl.split("?")[0].substring(uploadUrl.lastIndexOf("/")+1)

        val response = uploadApi.uploadImage(uploadUrl, reqFile)

        if(!response.isSuccessful) {
            return@withContext null
        }

        return@withContext assetId
    }

    suspend fun createPost(request: CreatePostRequestDto): PostDto? {
        val response = postApi.createPost(request)
        return response.body()

    }

}