package com.talky.mobile.ui.features.friendRequestsList

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Pas de nouvelles demandes d'amis")
                        Button(
                            onClick = { onPressBack() },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Go back",
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(text = "Retourner sur votre liste d'ami")
                        }
                    }

                }


            }

        }
    }
}

@Composable
fun FriendRequestConfirmDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    friendRequest: FriendRequestDto?,
    onShowProfile: (UserDto) -> Unit,
    onFriendRequestStatusChange: (FriendRequestDto.Status) -> Unit
) {
    if (isOpen && friendRequest != null)
        AlertDialog(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                ),
            onDismissRequest = { onClose() },
            title = {
                Text(
                    text = "Confirmer la demande d'ami ?",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "${friendRequest.sender!!.displayedName} vous a envoyé une invitation.")
                    OutlinedButton(
                        onClick = { onShowProfile(friendRequest.sender) },
                        modifier = Modifier.padding(top = 8.dp),
                    ) {
                        Text(text = "Voir le profil")
                    }
                }
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Button(
                        onClick = { onFriendRequestStatusChange(FriendRequestDto.Status.dENIED) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text = "Refuser")
                    }
                    Button(onClick = { onFriendRequestStatusChange(FriendRequestDto.Status.aCCEPTED) }) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Accept",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text = "Accepter")
                    }
                }
            }
        )
}
