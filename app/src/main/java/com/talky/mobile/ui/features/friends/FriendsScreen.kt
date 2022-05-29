package com.talky.mobile.ui.features.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.talky.mobile.api.models.FriendDto
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.commons.UserBar
import com.talky.mobile.ui.theme.VioletClair
import com.talky.mobile.ui.theme.VioletFonce
import kotlinx.coroutines.flow.Flow

@Composable
fun FriendsScreen(
    userList: Flow<PagingData<FriendDto>>,
    onFriendClick: (FriendDto) -> Unit,
    friendRequestList: List<FriendRequestDto>,
    onSeeFriendRequests: () -> Unit,
    onSearch: () -> Unit
) {
    val userListItems: LazyPagingItems<FriendDto> = userList.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable { onSearch() },
                        contentDescription = "Search users",
                        tint = Color.White
                    )
                },
                title = { Text("Vos amis", color = Color.White) },
                backgroundColor = VioletFonce
            )
        }
    ) {
        Column {
            if (friendRequestList.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Button(onClick = { onSeeFriendRequests() }) {
                        Text(text = "Vous avez ${friendRequestList.size} demandes d'amis")
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .background(VioletClair)
            ) {


                items(userListItems) { friendDto ->
                    val userDto = UserDto(
                        displayedName = friendDto!!.displayedName,
                        id = friendDto.id,
                        lastSeen = friendDto.lastSeen,
                        profilePicture = friendDto.profilePicture
                    )
                    UserBar(userDto, onClick = { onFriendClick(friendDto) })
                }
            }
        }

    }

}