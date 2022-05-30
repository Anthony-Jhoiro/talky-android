package com.talky.mobile.ui.commons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.talky.mobile.ui.NavigationKeys

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object FilActualite :
        NavigationItem(NavigationKeys.Route.FEED, Icons.Filled.Menu, "Fil d'actualité")

    object Profil : NavigationItem(NavigationKeys.Route.PROFILE, Icons.Filled.Person, "Profil")
    object Messages :
        NavigationItem(NavigationKeys.Route.FRIENDS, Icons.Filled.Notifications, "Messages")
}