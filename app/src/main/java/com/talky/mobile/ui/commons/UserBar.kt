package com.talky.mobile.ui.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.util.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

import coil.request.ImageRequest

@Composable
fun UserBar(author: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(15)
            ),
        shape = RoundedCornerShape(15)
    ) {

    Row(
        modifier = Modifier
            .padding(15.dp),

        verticalAlignment = Alignment.CenterVertically,
        ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(author.profilePicture)
                .size(150)
                .build(),
            contentScale = Crop,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = author.displayedName,
            fontSize = 20.sp,
        )

    }
    }
}