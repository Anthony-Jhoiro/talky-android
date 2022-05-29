package com.talky.mobile.ui.features.messaging

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.talky.mobile.api.models.MessageDto

@Composable
fun MessageScreen(
    messages: List<MessageDto>,
    sendMessage: (String) -> Unit
) {

    LazyColumn {
        items(messages) { messageDto ->
            Text(text = messageDto.toString())
        }
        item {
            Button(onClick = { sendMessage("Faut pas respirer la compote, ça fait tousser.") }) {
                Text(text = "Click me !")
            }
        }
    }

}