package com.talky.mobile.ui.features.postCreation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talky.mobile.api.services.TalkyAssetsRemoteSource
import com.talky.mobile.api.models.CreatePostRequestDto
import com.talky.mobile.api.models.PostDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCreationViewModel @Inject constructor(
    private val talkyAssetsRemoteSource: TalkyAssetsRemoteSource
) : ViewModel() {

    private suspend fun uploadAsset(asset: Bitmap): String? {
        return talkyAssetsRemoteSource.uploadAsset(asset)
    }

    fun onSubmit(textContent: String, privacy: PostDto.Privacy, images: List<Bitmap>) {
        viewModelScope.launch {
            val ids = images.mapNotNull { uploadAsset(it) }
            val request = CreatePostRequestDto(
                textContent,
                CreatePostRequestDto.Privacy.valueOf(privacy.name),
                ids
            )
            talkyAssetsRemoteSource.createPost(request)
        }
    }
}