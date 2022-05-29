package com.talky.mobile.ui.features.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.talky.mobile.api.pagingSource.TalkyPostsPagingSource
import com.talky.mobile.api.apis.PostControllerApi
import com.talky.mobile.api.models.PostDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val postControllerApi: PostControllerApi) :
    ViewModel() {
    val posts: Flow<PagingData<PostDto>> = Pager(PagingConfig(pageSize = 5)) {
        TalkyPostsPagingSource(postControllerApi)
    }.flow.cachedIn(viewModelScope)
}