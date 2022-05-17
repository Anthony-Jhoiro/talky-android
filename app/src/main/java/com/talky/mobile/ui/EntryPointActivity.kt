package com.talky.mobile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.talky.mobile.ui.commons.NavBar
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.talky.mobile.ui.NavigationKeys.Arg.IMAGE_URL
import com.talky.mobile.ui.features.fullScreenImage.FullScreenImageScreen
import com.talky.mobile.ui.features.fullScreenImage.FullScreenImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.talky.mobile.ui.theme.ComposeSampleTheme
import com.talky.mobile.ui.theme.TestPrimary


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
    Scaffold(
        bottomBar = {NavBar(navController)}
    ) {
        NavHost(navController, startDestination = NavigationKeys.Route.PROFILE) {
            composable(route = NavigationKeys.Route.FEED) {
                FeedScreenDestination(navController)
            }
            composable(route = NavigationKeys.Route.PROFILE) {
                ProfileScreenDestination()
            }
            composable(route = NavigationKeys.Route.FRIENDS) {
                FriendsScreenDestination()
            }
            composable(
                route = NavigationKeys.Route.FULL_SCREEN_IMAGE_ROUTE,
                arguments = listOf(
                    navArgument(IMAGE_URL) {
                        type = NavType.StringType
                    }
                )
            ) {
                FullScreenImageDestination(navController)
            }
        }
    }
}

@Composable
private fun FeedScreenDestination(navController: NavController) {
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

@Composable
private fun FullScreenImageDestination(navController: NavHostController) {
    val viewModel: FullScreenImageViewModel = hiltViewModel()
    FullScreenImageScreen(state = viewModel.state, onPressBack = {
        navController.popBackStack()
    })
}


object NavigationKeys {

    object Arg {
        const val IMAGE_URL = "imageUrl"
    }

    object Route {
        const val PROFILE = "profile"
        const val FEED = "feed"
        const val FRIENDS = "friends"
        const val FULL_SCREEN_IMAGE_ROUTE = "FullScreenImage?image={$IMAGE_URL}"
        const val FULL_SCREEN_IMAGE = "FullScreenImage?image="
    }
}


