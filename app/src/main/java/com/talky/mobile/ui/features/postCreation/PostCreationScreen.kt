package com.talky.mobile.ui.features.postCreation

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.ui.theme.VioletClair
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
    onSubmit: (String, PostDto.Privacy, List<Bitmap>) -> Unit
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
                FloatingActionButton(onClick = {
                    submitForm()
                }) {
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

@ExperimentalPermissionsApi
@Composable
fun PostCreationForm(
    textContent: String,
    onTextContentChanged: (String) -> Unit,
    selectedOption: Option<PostDto.Privacy>,
    onOptionSelected: (Option<PostDto.Privacy>) -> Unit,
    images: SnapshotStateList<Bitmap>,
    addImage: (bitmap: Bitmap?) -> Unit
) {
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            addImage(it)
        }

    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    Column(
        modifier = Modifier
            .background(VioletClair)
            .padding(8.dp)
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .height(300.dp)
        ) {
            TextField(
                value = textContent,
                onValueChange = { onTextContentChanged(it) },
                placeholder = {
                    Text(text = "Quoi de neuf ?")
                },
                modifier = Modifier
                    .fillMaxSize(),

                colors = textFieldColors(
                    backgroundColor = Color.White
                ),

                )
        }

        Spacer(modifier = Modifier.height(8.dp))

        PrivacySelector(selectedOption, onOptionSelected)

        Spacer(modifier = Modifier.height(8.dp))

        ImageSelector(
            images,
            onNewImagePress = {
                when (cameraPermissionState.status) {
                    is PermissionStatus.Denied -> {
                        cameraPermissionState.launchPermissionRequest()
                        cameraLauncher.launch()
                    }
                    PermissionStatus.Granted -> {
                        cameraLauncher.launch()
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(50.dp))


    }
}

@ExperimentalPermissionsApi
@Composable
fun ImageSelector(images: List<Bitmap>, onNewImagePress: () -> Unit) {
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
                .selectableGroup()
                .background(Color.White)
                .padding(8.dp)
        ) {
            Button(
                onClick = { onNewImagePress() },
                enabled = images.size < 10
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    modifier = Modifier
                        .padding(end = 12.dp),
                    contentDescription = "Go back"
                )
                Text(text = "Ajouter une photo")
            }

            Spacer(modifier = Modifier.height(5.dp))

            if (images.isNotEmpty()) {
                Row {
                    images.forEach {
                        Box(
                            modifier = Modifier.size(80.dp)
                        ) {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentScale = Crop,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PrivacySelector(
    selectedOption: Option<PostDto.Privacy>,
    onOptionSelected: (Option<PostDto.Privacy>) -> Unit
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
                .selectableGroup()
                .background(Color.White)
                .padding(8.dp)

        ) {
            Text(
                text = "Qui peut voir votre post ?",
                style = MaterialTheme.typography.caption.merge()
            )
            privacyOptions.forEach { option ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .selectable(
                            selected = option.value == selectedOption.value,
                            onClick = { onOptionSelected(option) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = option.value == selectedOption.value,
                        onClick = null
                    )
                    Text(
                        text = option.title,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun <T : Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> {
    return rememberSaveable(
        saver = listSaver(
            save = { stateList ->
                if (stateList.isNotEmpty()) {
                    val first = stateList.first()
                    if (!canBeSaved(first)) {
                        throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                    }
                }
                stateList.toList()
            },
            restore = { it.toMutableStateList() }
        )
    ) {
        elements.toList().toMutableStateList()
    }
}