package com.talky.mobile.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.talky.mobile.R
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.ui.commons.PostFrame
import com.talky.mobile.ui.theme.VioletClair
import com.talky.mobile.ui.theme.VioletFonce
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

    val (name, onNameChanged) = remember { mutableStateOf("") }


    val lazyUserPosts: LazyPagingItems<PostDto> = userPosts.collectAsLazyPagingItems()

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
                    backgroundColor = VioletFonce
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
            items(lazyUserPosts) { postDto ->
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
    if (openDialog) {
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
                Row {
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


