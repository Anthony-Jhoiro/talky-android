package com.talky.mobile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.talky.mobile.ui.commons.NavBar
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import com.talky.mobile.ui.NavigationKeys.Arg.IMAGE_URL
import com.talky.mobile.ui.features.fullScreenImage.FullScreenImageScreen
import com.talky.mobile.ui.features.fullScreenImage.FullScreenImageViewModel
import com.talky.mobile.ui.features.loading.LoadingScreen
import com.talky.mobile.ui.features.login.LoginScreen
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
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
    Scaffold(
        bottomBar = { NavBar(navController) }
    ) {
        NavHost(navController, startDestination = NavigationKeys.Route.FEED) {
            composable(route = NavigationKeys.Route.FEED) {
                FeedScreenDestination(navController)
            }
            composable(route = NavigationKeys.Route.PROFILE) {
                LoginRequired(authenticationViewModel, it) {
                    ProfileScreenDestination()
                }
            }
            composable(route = NavigationKeys.Route.FRIENDS) {
                LoginRequired(authenticationViewModel, it) {
                    FriendsScreenDestination()
                }
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

// Core pages

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

// Login pages

@Composable
private fun LoginRequired(authenticationViewModel: AuthenticationViewModel, navBackStackEntry: NavBackStackEntry, content: @Composable (NavBackStackEntry) -> Unit) {
    val context = LocalContext.current
    when {
        authenticationViewModel.isFetching.value -> {
            LoadingScreenDestination()
        }
        authenticationViewModel.isLoggedIn.value -> {
            content(navBackStackEntry)
        }
        else -> {
            LoginScreenDestination {
                authenticationViewModel.doLogin(context)
            }
        }
    }
}

@Composable
private fun LoginScreenDestination(onLogin: () -> Unit) {
    LoginScreen(
        onLogin,
    )
}

@Composable
private fun LoadingScreenDestination() {
    LoadingScreen()
}

@Composable
private fun FullScreenImageDestination(navController: NavHostController) {
    val viewModel: FullScreenImageViewModel = hiltViewModel()
    FullScreenImageScreen(state = viewModel.state, onPressBack = {
        navController.popBackStack()
    })
}

// Navigation controllers

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


