package com.talky.mobile.ui.features.userSearch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.commons.UserBar
import com.talky.mobile.ui.theme.VioletClair
import com.talky.mobile.ui.theme.VioletFonce
import kotlinx.coroutines.flow.Flow


@Composable
fun UserSearchScreen(
    userList: Flow<PagingData<UserDto>>, onUserClick: (UserDto) -> Unit,
    onPressBack: () -> Unit
) {
    val userListItems: LazyPagingItems<UserDto> = userList.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable { onPressBack() },
                        contentDescription = "Go back",
                        tint = Color.White
                    )
                },
                title = { Text("Rechercher un utilisateur", color = Color.White) },
                backgroundColor = VioletFonce
            )
        }
    ) {
        Column {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .background(VioletClair)
            ) {


                items(userListItems) { userDto ->
                    UserBar(userDto!!, onClick = { onUserClick(userDto) })
                }
            }
        }

    }

}