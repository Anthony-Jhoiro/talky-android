package com.talky.mobile.ui.commons

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

import coil.request.ImageRequest
import com.talky.mobile.R
import com.talky.mobile.api.models.UserDto
import java.time.OffsetDateTime

@Composable
fun UserBar(userDto: UserDto, onClick : () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(15)
            ).clickable{onClick()},
        shape = RoundedCornerShape(15)
    ) {

    Row(
        modifier = Modifier
            .padding(12.dp),

        verticalAlignment = Alignment.CenterVertically,
        ) {



        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)

                .data(userDto.profilePicture)
                .build(),
            error = painterResource(id =  R.drawable.ic_profile_default_picture),
            contentScale = Crop,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)

        )
        if(userDto.lastSeen!!.plusHours(2).isAfter(OffsetDateTime.now().minusMinutes(15))) {
            AsyncImage(
                model = R.drawable.present_indicator,
                contentScale = Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .offset(x = (-15).dp, y = (-15).dp)
            )

        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = userDto.displayedName!!,
            fontSize = 24.sp,
        )

    }
    }
}