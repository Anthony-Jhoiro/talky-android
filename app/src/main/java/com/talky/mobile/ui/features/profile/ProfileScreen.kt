package com.talky.mobile.ui.features.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.talky.mobile.api.models.UserDto

import com.talky.mobile.ui.theme.VioletClair

@Composable
fun ProfileScreen(
    state: ProfileScreenContract.State,
    viewModel: ProfileScreenViewModel
) {
    var name by remember { mutableStateOf("") }

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
                if(state.myProfile) {
                    OutlinedTextField(
                        modifier = Modifier.padding(6.dp),
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Entrez votre nouveau nom") }
                    )

                    Button(onClick = { viewModel.updateProfile(name) }, modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)) {
                        Icon(Icons.Filled.Edit,
                            contentDescription = "Editer",
                        )
                    }
                }

            }
            Row {
                Text(text= state.profile?.displayedName.toString(),
                    modifier = Modifier
                        .width(100.dp)
                        .align(CenterVertically))
            }
        }
    }
}


