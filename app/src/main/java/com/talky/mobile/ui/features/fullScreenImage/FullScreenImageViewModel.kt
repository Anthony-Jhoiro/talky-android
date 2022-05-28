package com.talky.mobile.ui.features.fullScreenImage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talky.mobile.ui.NavigationKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FullScreenImageViewModel @Inject constructor(
        private val stateHandle: SavedStateHandle,
) : ViewModel() {


    var state by mutableStateOf(
            FullScreenAssetContract.State(
                    null
            )
    )
        private set

    init {
        viewModelScope.launch {
            val imageUrl = stateHandle.get<String>(NavigationKeys.Arg.IMAGE_URL)
                    ?: throw IllegalStateException("No categoryId was passed to destination.")
            state = state.copy(url = String(Base64.getDecoder().decode(imageUrl)))
        }
    }
}