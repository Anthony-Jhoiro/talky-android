package com.talky.mobile.ui.features.friendRequestsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talky.mobile.api.TalkyFriendRequestListRemoteSource
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.ui.features.postCreation.rememberMutableStateListOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendRequestListViewModel @Inject constructor(
    private val talkyFriendRequestListRemoteSource: TalkyFriendRequestListRemoteSource
): ViewModel() {

    var state by mutableStateOf(FriendRequestListContract.State(listOf()))

    val friendRequestList: List<FriendRequestDto>
        get() {
            return state.friendRequestsList
        }


    init {
        viewModelScope.launch {
            val friendRequestList = talkyFriendRequestListRemoteSource.getFriendRequests()
            state = state.copy(friendRequestsList = friendRequestList)
        }
    }
}

