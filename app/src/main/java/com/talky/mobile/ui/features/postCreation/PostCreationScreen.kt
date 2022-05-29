package com.talky.mobile.ui.features.postCreation

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.ui.theme.VioletFonce

class Option<T>(
    val title: String, val value: T
)

val privacyOptions = listOf(
    Option("Tout le monde", PostDto.Privacy.pUBLIC),
    Option("Vous et vos amis", PostDto.Privacy.pRIVATE)
)

@ExperimentalPermissionsApi
@Composable
fun PostCreationScreen(
    onPressBack: () -> Unit,
    onSubmit: (String, PostDto.Privacy, List<Bitmap>) -> Unit,
    onValidateCreation: () -> Unit
) {

    val (textContent, onTextContentChanged) = remember { mutableStateOf("") }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(privacyOptions[0]) }
    val images = rememberMutableStateListOf<Bitmap>()

    var isFetching by remember { mutableStateOf(false) }

    fun addImage(bitmap: Bitmap?) {
        if (bitmap != null && images.size < 10) images.add(bitmap)
    }

    fun submitForm() {
        isFetching = true
        onSubmit(
            textContent,
            selectedOption.value,
            images
        )
        isFetching = false
        onValidateCreation()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (!isFetching)
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .clickable { onPressBack() },
                            contentDescription = "Go back",
                            tint = Color.White
                        )
                },

                title = { Text("Créer un post", color = Color.White) },
                backgroundColor = VioletFonce
            )
        },
        floatingActionButton = {
            if (!isFetching) {
                FloatingActionButton(
                    onClick = {
                        submitForm()
                    },
                    backgroundColor = VioletFonce
                ) {
                    Icon(Icons.Filled.Check, "Save Post")
                }
            }
        }
    )
    {
        if (!isFetching) {

            PostCreationForm(
                textContent, onTextContentChanged, selectedOption, onOptionSelected, images
            ) { addImage(it) }
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

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
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.background(Color.White)
                    ) {
                        Text(text = "Votre post est en cours de création")
                        Spacer(modifier = Modifier.height(12.dp))
                        CircularProgressIndicator()
                    }
                }

            }
        }

    }
}

