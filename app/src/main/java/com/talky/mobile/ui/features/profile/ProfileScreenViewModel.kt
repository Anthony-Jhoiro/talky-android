package com.talky.mobile.ui.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.talky.mobile.api.services.TalkyFriendsService
import com.talky.mobile.api.pagingSource.TalkyUserPostsRemoteSource
import com.talky.mobile.api.services.TalkyUsersService
import com.talky.mobile.api.apis.PostControllerApi
import com.talky.mobile.api.models.CreateFriendRequestRequestDto
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.api.models.UpdateUserRequestDto
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.NavigationKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val usersService: TalkyUsersService,
    private val stateHandle: SavedStateHandle,
    private val friendsService: TalkyFriendsService,
    private val postControllerApi: PostControllerApi
) :
    ViewModel() {
    var userPosts: Flow<PagingData<PostDto>>? = null

    var state by mutableStateOf(
        ProfileScreenContract.State(
            profile = null,
            myProfile = false
        )
    )

    init {
        viewModelScope.launch {
            getProfile()
            userPosts = Pager(PagingConfig(pageSize = 5)) {
                TalkyUserPostsRemoteSource(
                    postControllerApi,
                    state.profile?.id!!
                )
            }.flow.cachedIn(viewModelScope)
        }

    }

    private suspend fun getProfile() {
        val profileId = stateHandle.get<String>(NavigationKeys.Arg.PROFILE_ID)
        val profile: UserDto
        if (profileId != null) {
            profile = usersService.getUserById(UUID.fromString(profileId))!!
        } else {
            profile = usersService.getProfile()!!
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
            usersService.updateDisplayedName(request)
            getProfile()
        }
    }

    fun addFriend() {
        val request = CreateFriendRequestRequestDto(
            recipient = state.profile?.id
        )
        viewModelScope.launch {
            friendsService.createFriendRequest(request)
            getProfile()
        }
    }
}