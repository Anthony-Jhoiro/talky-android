package com.talky.mobile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.talky.mobile.ui.NavigationKeys.Arg.IMAGE_URL
import com.talky.mobile.ui.NavigationKeys.Arg.PROFILE_ID
import com.talky.mobile.ui.NavigationKeys.Route.POST_CREATION
import com.talky.mobile.ui.NavigationKeys.Route.PROFILE
import com.talky.mobile.ui.commons.NavBar
import com.talky.mobile.ui.features.feed.FeedScreen
import com.talky.mobile.ui.features.feed.FeedViewModel
import com.talky.mobile.ui.features.friends.FriendsScreen
import com.talky.mobile.ui.features.friends.FriendsScreenViewModel
import com.talky.mobile.ui.features.fullScreenImage.FullScreenImageScreen
import com.talky.mobile.ui.features.fullScreenImage.FullScreenImageViewModel
import com.talky.mobile.ui.features.loading.LoadingScreen
import com.talky.mobile.ui.features.login.LoginScreen
import com.talky.mobile.ui.features.postCreation.PostCreationScreen
import com.talky.mobile.ui.features.postCreation.PostCreationViewModel
import com.talky.mobile.ui.features.profile.ProfileScreen
import com.talky.mobile.ui.features.profile.ProfileScreenViewModel
import com.talky.mobile.ui.theme.ComposeSampleTheme
import com.talky.mobile.ui.theme.VioletClair
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
@ExperimentalPermissionsApi
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
@ExperimentalPermissionsApi
private fun TalkyApp() {
    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
    Scaffold(
        bottomBar = { NavBar(navController) },
        modifier = Modifier
            .background(VioletClair)
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController, startDestination = NavigationKeys.Route.FEED) {
                composable(route = NavigationKeys.Route.FEED) {
                    FeedScreenDestination(navController, authenticationViewModel)
                }
                composable(route = NavigationKeys.Route.PROFILE) {
                    LoginRequired(authenticationViewModel, it) {
                        ProfileScreenDestination(navController, authenticationViewModel)

                    }
                }

                composable(route = POST_CREATION) {
                    PostCreationScreenDestination(navController)
                }

                composable(route = NavigationKeys.Route.PROFILE) {
                    LoginRequired(authenticationViewModel, it) {
                        ProfileScreenDestination(navController, authenticationViewModel)
                    }
                }
                composable(route = NavigationKeys.Route.FRIENDS) {
                    LoginRequired(authenticationViewModel, it) {
                        FriendsScreenDestination(navController)
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
                composable(
                    route = NavigationKeys.Route.USER_PROFILE,
                    arguments = listOf(
                        navArgument(PROFILE_ID) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    ProfileScreenDestination(navController = navController, authenticationViewModel = authenticationViewModel)
                }
            }
        }
    }
}

fun openFullScreenImagePage(image: String, navController: NavController) {
    val b64Url = Base64.getEncoder().encodeToString(image.toByteArray())
    navController.navigate(NavigationKeys.Route.FULL_SCREEN_IMAGE + b64Url)
}

// Core pages

@Composable
private fun FeedScreenDestination(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel
) {
    val viewModel: FeedViewModel = hiltViewModel()
    FeedScreen(
        postList = viewModel.posts,
        onOpenAsset = {
            openFullScreenImagePage(it, navController)
        },
        onAddButtonPressed = {
            navController.navigate(POST_CREATION)
        },
        isLoggedIn = authenticationViewModel.isLoggedIn.value
    )
}

@ExperimentalPermissionsApi
@Composable
private fun PostCreationScreenDestination(navController: NavController) {
    val viewModel: PostCreationViewModel = hiltViewModel()
    PostCreationScreen(
        onPressBack = {
            navController.popBackStack()
        },
        onSubmit = { textContent, privacy, images ->
            viewModel.onSubmit(textContent, privacy, images)
        }
    )
}

@Composable
private fun ProfileScreenDestination(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel,

) {
    //ProfileScreen()
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    if (viewModel.state.profile?.id == authenticationViewModel.profile.value?.id) {
        viewModel.state.myProfile = true;
    }
    ProfileScreen(
        state = viewModel.state,
        viewModel = viewModel,
        onPressBack = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun FriendsScreenDestination(navController: NavController) {
    val viewModel : FriendsScreenViewModel = hiltViewModel()
    FriendsScreen(
        userList = viewModel.users,
        onUserClick = {
            navController.navigate(PROFILE + "/" + it.id)
        }
    )
}

// Login pages

@Composable
private fun LoginRequired(
    authenticationViewModel: AuthenticationViewModel,
    navBackStackEntry: NavBackStackEntry,
    content: @Composable (NavBackStackEntry) -> Unit
) {
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
        const val PROFILE_ID = "id"
    }


    object Route {
        const val PROFILE = "profile"
        const val USER_PROFILE = "profile/{$PROFILE_ID}"
        const val FEED = "feed"
        const val FRIENDS = "friends"
        const val FULL_SCREEN_IMAGE_ROUTE = "FullScreenImage?image={$IMAGE_URL}"
        const val FULL_SCREEN_IMAGE = "FullScreenImage?image="
        const val POST_CREATION = "createPost"

    }
}


