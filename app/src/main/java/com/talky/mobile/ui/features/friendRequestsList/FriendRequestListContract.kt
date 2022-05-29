package com.talky.mobile.ui.features.friendRequestsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.ui.features.postCreation.rememberMutableStateListOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendRequestListContract @Inject constructor(): ViewModel() {
    data class State (
        val friendRequestsList: List<FriendRequestDto>
    )
}

