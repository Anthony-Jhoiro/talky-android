package com.talky.mobile.ui.features.friendRequestsList

import android.widget.Toast
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendRequestListViewModel @Inject constructor(
    private val talkyFriendRequestListRemoteSource: TalkyFriendRequestListRemoteSource
): ViewModel() {
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    var state by mutableStateOf(FriendRequestListContract.State(listOf()))

    val friendRequestList: List<FriendRequestDto>
        get() {
            return state.friendRequestsList
        }

    private suspend fun loadFriendRequest() {
        val friendRequestList = talkyFriendRequestListRemoteSource.getFriendRequests()
        state = state.copy(friendRequestsList = friendRequestList)
    }

    init {
        viewModelScope.launch {
            loadFriendRequest()
        }
    }


    fun changeFriendRequestStatus(fr: FriendRequestDto, status: FriendRequestDto.Status) {
        viewModelScope.launch {
            val didSucceed = talkyFriendRequestListRemoteSource.changeFriendRequestStatus(fr, status)
            if (didSucceed) {
                if (status == FriendRequestDto.Status.dENIED) {
                    _toastMessage.emit("La demande d'ami a été supprimée")
                } else {
                    _toastMessage.emit("L'utilisateur a bien été ajouté à vos amis")
                }
            } else {
                _toastMessage.emit("Une erreur est survenue")
            }

            talkyFriendRequestListRemoteSource.reset()
            loadFriendRequest()
        }
    }
}
