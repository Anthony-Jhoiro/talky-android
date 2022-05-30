package com.talky.mobile.ui.features.friendRequestsList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.api.models.UserDto

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