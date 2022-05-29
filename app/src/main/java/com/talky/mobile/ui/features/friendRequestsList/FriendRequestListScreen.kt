package com.talky.mobile.ui.features.friendRequestsList

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.commons.UserBar
import com.talky.mobile.ui.theme.VioletClair
import com.talky.mobile.ui.theme.VioletFonce
import kotlinx.coroutines.flow.SharedFlow


@Composable
fun FriendRequestListScreen(
    friendRequestList: List<FriendRequestDto>,
    onPressBack: () -> Unit,
    onShowProfile: (UserDto) -> Unit,
    onFriendRequestStatusChange: (FriendRequestDto, FriendRequestDto.Status) -> Unit,
    toastMessage: SharedFlow<String>
) {
    val context = LocalContext.current

    var openDialog by remember { mutableStateOf(true) }
    var selectedUser: FriendRequestDto? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }

    fun onSelectUser(userDto: FriendRequestDto) {
        selectedUser = userDto
        openDialog = true
    }


    FriendRequestConfirmDialog(
        isOpen = openDialog,
        onClose = { openDialog = false },
        friendRequest = selectedUser,
        onFriendRequestStatusChange = {
            onFriendRequestStatusChange(selectedUser!!, it)
            openDialog = false
        },
        onShowProfile = {
            onShowProfile(it)
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable { onPressBack() },
                        contentDescription = "Go back",
                        tint = Color.White
                    )
                },

                title = { Text("Vos demandes d'amis", color = Color.White) },
                backgroundColor = VioletFonce
            )
        }
    ) {
        if (friendRequestList.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .background(VioletClair)
            ) {


                items(friendRequestList) { friendshipDto ->
                    UserBar(friendshipDto.sender!!, onClick = { onSelectUser(friendshipDto) })
                }
            }
        } else {
            FriendRequestListEmptyMessage {
                onPressBack()
            }
        }
    }
}

