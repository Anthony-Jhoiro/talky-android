package com.talky.mobile.ui.features.friendRequestsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talky.mobile.api.services.TalkyFriendRequestListService
import com.talky.mobile.api.models.FriendRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendRequestListViewModel @Inject constructor(
    private val talkyFriendRequestListService: TalkyFriendRequestListService
) : ViewModel() {
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    var state by mutableStateOf(FriendRequestListContract.State(listOf()))

    val friendRequestList: List<FriendRequestDto>
        get() {
            return state.friendRequestsList
        }

    private suspend fun loadFriendRequest() {
        val friendRequestList = talkyFriendRequestListService.getFriendRequests()
        state = state.copy(friendRequestsList = friendRequestList)
    }

    init {
        viewModelScope.launch {
            loadFriendRequest()
        }
    }


    fun changeFriendRequestStatus(fr: FriendRequestDto, status: FriendRequestDto.Status) {
        viewModelScope.launch {
            val didSucceed =
                talkyFriendRequestListService.changeFriendRequestStatus(fr, status)
            if (didSucceed) {
                if (status == FriendRequestDto.Status.dENIED) {
                    _toastMessage.emit("La demande d'ami a été supprimée")
                } else {
                    _toastMessage.emit("L'utilisateur a bien été ajouté à vos amis")
                }
            } else {
                _toastMessage.emit("Une erreur est survenue")
            }

            talkyFriendRequestListService.reset()
            loadFriendRequest()
        }
    }
}

