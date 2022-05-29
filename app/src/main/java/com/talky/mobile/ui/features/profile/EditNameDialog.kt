package com.talky.mobile.ui.features.profile

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

fun closeAndEdit(
    viewModel: ProfileScreenViewModel,
    name: String,
    onClose: () -> Unit
) {
    viewModel.updateProfile(name)
    onClose()
}

@Composable
fun EditNameDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    viewModel: ProfileScreenViewModel,
    name: String,
    onNameChanged: (String) -> Unit,
) {
    if (isOpen)
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
                    text = "Entrez votre nouveau nom",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.padding(start = 12.dp),
                        value = name,
                        onValueChange = { onNameChanged(it) },
                        label = { Text("Entrez votre nouveau nom") }
                    )
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
                        onClick = { onClose() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Cancel",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text = "Annuler")
                    }
                    Button(onClick = { closeAndEdit(viewModel, name, onClose) }) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Modify",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text = "Modifier")
                    }
                }
            }
        )
}