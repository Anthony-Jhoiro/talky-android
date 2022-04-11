package com.talky.mobile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import com.talky.mobile.ui.theme.ComposeSampleTheme


@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleTheme {
                TalkyApp()
            }
        }
    }
}

@Preview
@Composable
private fun TalkyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationKeys.Route.FEED) {
        composable(route = NavigationKeys.Route.FEED) {
            FeedScreenDestination()
        }
        composable(route = NavigationKeys.Route.PROFILE) {
            ProfileScreenDestination()
        }
        composable(route = NavigationKeys.Route.FRIENDS) {
            FriendsScreenDestination()
        }
    }
}

@Composable
private fun FeedScreenDestination() {
    Text(text = "Feed screen")
}

@Composable
private fun ProfileScreenDestination() {
    Text(text = "Profile screen")
}

@Composable
private fun FriendsScreenDestination() {
    Text(text = "Friends screen")
}


object NavigationKeys {
    object Route {
        const val PROFILE = "profile"
        const val FEED = "feed"
        const val FRIENDS = "friends"
    }
}


