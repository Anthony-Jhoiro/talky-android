package com.talky.mobile.ui.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talky.mobile.api.TalkyUsersRemoteSource
import com.talky.mobile.api.models.UpdateUserRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val remoteSource: TalkyUsersRemoteSource,
) :
    ViewModel() {

    var state by mutableStateOf(
        ProfileScreenContract.State(
            profile = null,
            myProfile = false
        )
    )

   init {
       viewModelScope.launch { getProfile() }
   }

    private suspend fun getProfile() {
        val profile = remoteSource.getProfile()
        viewModelScope.launch {
            state = state.copy(profile = profile)
        }
    }

    fun updateProfile(name: String) {
        val request = UpdateUserRequestDto(
            displayedName = name,
            profilePicture = null
        )
        viewModelScope.launch {
            remoteSource.updateDisplayedName(request)
            getProfile()
        }
    }
}