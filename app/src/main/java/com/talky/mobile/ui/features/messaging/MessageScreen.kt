package com.talky.mobile.ui.features.messaging

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.talky.mobile.R
import com.talky.mobile.api.models.FriendshipDto
import com.talky.mobile.api.models.MessageDto
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.commons.PrivateMessageFriend
import com.talky.mobile.ui.commons.PrivateMessageSelf
import com.talky.mobile.ui.theme.VioletFonce

@Composable
fun MessageScreen(
    messages: List<MessageDto>,
    sendMessage: (String) -> Unit,
    friendship: FriendshipDto?,
    currentUser: UserDto?,
    onPressBack: () -> Unit
) {
    var messageFieldValue by remember { mutableStateOf("") }

    fun onMessageSend() {
        sendMessage(messageFieldValue)
        messageFieldValue = ""
    }

    if (friendship == null || currentUser == null) {
        Scaffold {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        return
    }

    val otherUser = friendship.friends?.first { it.id != currentUser.id }!!

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
                title = {

                    Card(
                        shape = CircleShape,
                    ) {
                        AsyncImage(
                            model = otherUser.profilePicture,
                            contentDescription = null,
                            error = painterResource(id = R.drawable.ic_profile_default_picture)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(otherUser.displayedName!!, color = Color.White)
                },
                backgroundColor = VioletFonce
            )
        }
    ) {
        Column {

            LazyColumn(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.Bottom
            ) {
                items(messages) { messageDto ->

                    if (messageDto.author == otherUser.id) {
                        PrivateMessageFriend(messageDto)
                    } else {
                        PrivateMessageSelf(messageDto)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageFieldValue,
                    onValueChange = { messageFieldValue = it },
                    placeholder = {
                        Text(text = "Votre message")
                    },
                    modifier = Modifier
                        .weight(1F),

                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    ),
                )
                Button(
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.size(50.dp),
                    onClick = {
                        onMessageSend()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send message",
                        tint = Color.White
                    )
                }
            }
        }
    }


}