package com.talky.mobile.ui.features.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.google.firebase.annotations.PreviewApi
import com.talky.mobile.R
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.theme.VioletClair
import com.talky.mobile.ui.theme.VioletFonce

@Composable
fun ProfileScreen(
    state: ProfileScreenContract.State,

) {
    val image: Painter = painterResource(id = R.drawable.ic_profile_default_picture)

    Scaffold(
        backgroundColor = VioletClair
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .background(Color.White)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)

                ) {
                    AsyncImage(model = state.profile?.profilePicture.toString(), contentDescription = "")
                }
            Row {
                Text(text= state.profile?.displayedName.toString(),
                    modifier = Modifier.width(100.dp).align(CenterVertically))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Modifier")
                }
            }
            

            }

        }/*
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .background(Color.White)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text= "Liste des posts : ", modifier = Modifier.width(100.dp))
            }
        }*/
    }

