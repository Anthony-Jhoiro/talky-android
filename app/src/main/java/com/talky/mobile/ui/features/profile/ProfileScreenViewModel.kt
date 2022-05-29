package com.talky.mobile.ui.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talky.mobile.api.TalkyFriendsRemoteSource
import com.talky.mobile.api.TalkyUsersRemoteSource
import com.talky.mobile.api.models.CreateFriendRequestRequestDto
import com.talky.mobile.api.models.UpdateUserRequestDto
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.NavigationKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val usersRemoteSource: TalkyUsersRemoteSource,
    private val stateHandle : SavedStateHandle,
    private val friendsRemoteSource: TalkyFriendsRemoteSource
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
        val profileId = stateHandle.get<String>(NavigationKeys.Arg.PROFILE_ID)
        val profile: UserDto
        if(profileId!= null) {
            profile = usersRemoteSource.getUserById(UUID.fromString(profileId))!!
        } else {
            profile = usersRemoteSource.getProfile()!!
        }
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
            usersRemoteSource.updateDisplayedName(request)
            getProfile()
        }
    }

    fun addFriend() {
        val request = CreateFriendRequestRequestDto(
            recipient = state.profile?.id
        )
        viewModelScope.launch {
            friendsRemoteSource.createFriendRequest(request)
            getProfile()
        }
    }
}