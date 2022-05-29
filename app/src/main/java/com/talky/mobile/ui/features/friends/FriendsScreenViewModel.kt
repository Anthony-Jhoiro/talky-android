package com.talky.mobile.ui.features.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.talky.mobile.api.pagingSource.TalkyFriendshipPagingSource
import com.talky.mobile.api.apis.FriendshipControllerApi
import com.talky.mobile.api.models.FriendDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FriendsScreenViewModel @Inject constructor(private val friendshipControllerApi: FriendshipControllerApi) :
    ViewModel() {
    val friends: Flow<PagingData<FriendDto>> = Pager(PagingConfig(pageSize = 10)) {
        TalkyFriendshipPagingSource(friendshipControllerApi)
    }.flow.cachedIn(viewModelScope)
}