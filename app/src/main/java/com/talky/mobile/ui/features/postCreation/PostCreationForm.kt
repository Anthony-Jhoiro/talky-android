package com.talky.mobile.ui.features.postCreation

import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.ui.theme.VioletClair

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
        rememberPermissionState(permission = Manifest.permission.CAMERA)

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

                colors = TextFieldDefaults.textFieldColors(
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