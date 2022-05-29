package com.talky.mobile.ui.features.postCreation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi

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
                                contentScale = ContentScale.Crop,
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