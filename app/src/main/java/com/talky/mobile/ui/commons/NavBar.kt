package com.talky.mobile.ui.commons

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.talky.mobile.ui.theme.BleuCyan
import com.talky.mobile.ui.theme.VioletClair
import com.talky.mobile.ui.theme.VioletFonce

@Composable
fun NavBar(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        NavigationItem.FilActualite,
        NavigationItem.Profil,
        NavigationItem.Messages
    )

    BottomNavigation(
        contentColor = Color.Black,
        backgroundColor = VioletFonce,



    )
    {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.title) },
                selected = selectedItem == index,
                selectedContentColor = Color.White,
                onClick = { selectedItem = index
                    navController.navigate(item.route)
                }
            )
        }
    }
}