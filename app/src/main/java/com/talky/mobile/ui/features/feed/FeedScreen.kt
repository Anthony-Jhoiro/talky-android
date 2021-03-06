package com.talky.mobile.ui.features.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.ui.commons.PostFrame
import com.talky.mobile.ui.theme.VioletClair
import com.talky.mobile.ui.theme.VioletFonce
import kotlinx.coroutines.flow.Flow

@Composable
fun FeedScreen(
    postList: Flow<PagingData<PostDto>>,
    onOpenAsset: (String) -> Unit,
    onAddButtonPressed: () -> Unit,
    isLoggedIn: Boolean
) {
    val postListItems: LazyPagingItems<PostDto> = postList.collectAsLazyPagingItems()

    Scaffold(
        floatingActionButton = {
            if (isLoggedIn) {
                FloatingActionButton(
                    onClick = { onAddButtonPressed() },
                    backgroundColor = VioletFonce
                ) {
                    Icon(Icons.Filled.Add, "Localized description")
                }
            }

        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier
                .background(VioletClair)
        ) {
            items(postListItems) { postDto ->
                PostFrame(postDto!!, onOpenAsset)
            }
        }
    }


}