package com.talky.mobile.ui.features.userSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.talky.mobile.api.TalkyUserListRemoteSource
import com.talky.mobile.api.apis.UserControllerApi
import com.talky.mobile.api.models.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserSearchScreenViewModel @Inject constructor(private val userControllerApi: UserControllerApi) :
    ViewModel() {
    val users: Flow<PagingData<UserDto>> = Pager(PagingConfig(pageSize = 10)) {
        TalkyUserListRemoteSource(userControllerApi)
    }.flow.cachedIn(viewModelScope)
}