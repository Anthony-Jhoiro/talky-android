package com.talky.mobile.ui.features.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.talky.mobile.R
import com.talky.mobile.api.models.UserDto

import com.talky.mobile.ui.theme.VioletClair

@Composable
fun ProfileScreen(
    state: ProfileScreenContract.State,
    viewModel: ProfileScreenViewModel,
    logout: () -> Unit,
    onPressBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }

    Scaffold(
        backgroundColor = VioletClair,
        topBar = {
            if (!state.myProfile) {
                TopAppBar(
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .clickable { onPressBack() },
                            contentDescription = "Go back"
                        )
                    },

                    title = { Text("Retour") },
                    backgroundColor = MaterialTheme.colors.background
                )
            }
        }
    ) {


        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .background(Color.White)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            if(state.myProfile) {
                Row() {
                    Button(
                        onClick = { logout() }, modifier = Modifier
                            .padding(6.dp)
                    ) {
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = "Logout",
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Me déconnecter")
                    }
                }
            }
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)

            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)

                        .data(state.profile?.profilePicture)
                        .build(),
                    contentDescription = "",
                    error = painterResource(id = R.drawable.ic_profile_default_picture)
                )

            }
            Row {
                if (state.myProfile) {
                    OutlinedTextField(
                        modifier = Modifier.padding(6.dp),
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Entrez votre nouveau nom") }
                    )

                    Button(
                        onClick = { viewModel.updateProfile(name) }, modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Editer",
                        )
                    }
                }

            }
            Row {
                Text(
                    text = state.profile?.displayedName.toString(),
                    modifier = Modifier
                        .width(100.dp)
                        .align(CenterVertically)
                )
            }
        }
    }
}


