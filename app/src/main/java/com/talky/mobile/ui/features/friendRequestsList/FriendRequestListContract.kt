package com.talky.mobile.ui.features.friendRequestsList

import androidx.lifecycle.ViewModel
import com.talky.mobile.api.models.FriendRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendRequestListContract @Inject constructor() : ViewModel() {
    data class State(
        val friendRequestsList: List<FriendRequestDto>
    )
}

