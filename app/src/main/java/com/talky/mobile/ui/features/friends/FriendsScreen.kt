package com.talky.mobile.ui.features.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.commons.User
import com.talky.mobile.ui.commons.UserBar
import com.talky.mobile.ui.theme.VioletClair
import kotlinx.coroutines.flow.Flow

@Composable
fun FriendsScreen(
    userList: Flow<PagingData<UserDto>>, onUserClick: (UserDto) -> Unit,
    friendRequestList: List<FriendRequestDto>,
    onSeeFriendRequests: () -> Unit
) {
    val userListItems: LazyPagingItems<UserDto> = userList.collectAsLazyPagingItems()

    Scaffold(

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


                items(userListItems) { userDto ->
                    UserBar(userDto!!, onClick = { onUserClick(userDto) })
                }
            }
        }

    }

}