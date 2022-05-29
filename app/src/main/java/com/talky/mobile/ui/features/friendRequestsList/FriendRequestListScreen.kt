package com.talky.mobile.ui.features.friendRequestsList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.ui.commons.UserBar
import com.talky.mobile.ui.theme.VioletClair
import com.talky.mobile.ui.theme.VioletFonce


@Composable
fun FriendRequestListScreen(
    friendRequestList: List<FriendRequestDto>
) {
    Scaffold {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier
                .background(VioletClair)
        ) {


            items(friendRequestList) { friendshipDto ->
                UserBar(friendshipDto.sender!!, onClick = { })
            }
        }
    }
}
