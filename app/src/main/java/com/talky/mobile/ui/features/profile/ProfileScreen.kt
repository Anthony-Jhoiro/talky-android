package com.talky.mobile.ui.features.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.solver.state.helpers.AlignHorizontallyReference
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.talky.mobile.R
import com.talky.mobile.api.models.FriendRequestDto
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.commons.PostFrame

import com.talky.mobile.ui.theme.VioletClair
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    state: ProfileScreenContract.State,
    viewModel: ProfileScreenViewModel,
    logout: () -> Unit,
    onPressBack: () -> Unit,
    userPosts: Flow<PagingData<PostDto>>,
    onOpenAsset: (String) -> Unit
) {

    val(name, onNameChanged) = remember { mutableStateOf("") }


    val userPosts: LazyPagingItems<PostDto> = userPosts.collectAsLazyPagingItems()

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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier
                .background(VioletClair)
        ) {
            item {
                ProfilComposant(state, logout, name, onNameChanged, viewModel)
            }
            items(userPosts) { postDto ->
                PostFrame(post = postDto!!, openAsset = onOpenAsset)
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun ProfilComposant(
    state: ProfileScreenContract.State,
    logout: () -> Unit,
    name: String,
    onNameChanged: (String) -> Unit,
    viewModel: ProfileScreenViewModel
) {
    var openDialog by remember { mutableStateOf(false) }

    fun onOpenEdit() {
        openDialog = true

    }
    if(openDialog) {
        EditNameDialog(
            isOpen = openDialog,
            onClose = { openDialog = false },
            viewModel = viewModel,
            name = name,
            onNameChanged = onNameChanged
        )
    }

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
                .background(Color.White)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (state.myProfile) {
                    Button(
                        onClick = { onOpenEdit() },
                        modifier = Modifier
                            .padding(top = 10.dp, start = 6.dp, end = 12.dp)

                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Editer",
                        )
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
                Text(
                    text = state.profile?.displayedName.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
           /* Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                    OutlinedTextField(
                        modifier = Modifier.padding(start = 12.dp),
                        value = name,
                        onValueChange = { onNameChanged(it) },
                        label = { Text("Entrez votre nouveau nom") }
                    )
                    Button(
                        onClick = { viewModel.updateProfile(name) },
                        modifier = Modifier
                            .padding(start = 6.dp, end = 12.dp),
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Editer",
                        )
                    }
                }
            }*/
            Row {
                if (!state.myProfile) {
                    Button(
                        onClick = { viewModel.addFriend() }, modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                    ) {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "AddFriend",
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Ajouter un ami")
                    }
                }
            }
            if (state.myProfile) {
                Row() {
                    Button(
                        onClick = { logout() }, modifier = Modifier
                            .padding(6.dp)
                            .fillMaxSize()
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
        }
    }
}

@Composable
fun EditNameDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    viewModel: ProfileScreenViewModel,
    name: String,
    onNameChanged: (String) -> Unit,
) {
    if (isOpen != null)
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
                    Button(onClick = { closeAndEdit(viewModel,name,onClose) }) {
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
fun closeAndEdit(
    viewModel: ProfileScreenViewModel,
    name: String,
    onClose: () -> Unit
) {
    viewModel.updateProfile(name)
    onClose()
}


