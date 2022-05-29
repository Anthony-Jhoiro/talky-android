package com.talky.mobile.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.talky.mobile.ui.NavigationKeys.Arg.FRIENDSHIP_ID
import com.talky.mobile.ui.NavigationKeys.Arg.IMAGE_URL
import com.talky.mobile.ui.NavigationKeys.Arg.PROFILE_ID
import com.talky.mobile.ui.commons.NavBar
import com.talky.mobile.ui.features.feed.FeedScreen
import com.talky.mobile.ui.features.feed.FeedViewModel
import com.talky.mobile.ui.features.friendRequestsList.FriendRequestListScreen
import com.talky.mobile.ui.features.friendRequestsList.FriendRequestListViewModel
import com.talky.mobile.ui.features.friends.FriendsScreen
import com.talky.mobile.ui.features.friends.FriendsScreenViewModel
import com.talky.mobile.ui.features.fullScreenImage.FullScreenImageScreen
import com.talky.mobile.ui.features.fullScreenImage.FullScreenImageViewModel
import com.talky.mobile.ui.features.loading.LoadingScreen
import com.talky.mobile.ui.features.login.LoginScreen
import com.talky.mobile.ui.features.messaging.MessageScreen
import com.talky.mobile.ui.features.messaging.MessageScreenViewModel
import com.talky.mobile.ui.features.postCreation.PostCreationScreen
import com.talky.mobile.ui.features.postCreation.PostCreationViewModel
import com.talky.mobile.ui.features.profile.ProfileScreen
import com.talky.mobile.ui.features.profile.ProfileScreenViewModel
import com.talky.mobile.ui.features.userSearch.UserSearchScreen
import com.talky.mobile.ui.features.userSearch.UserSearchScreenViewModel
import com.talky.mobile.ui.theme.ComposeSampleTheme
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
                composable(route = NavigationKeys.Route.POST_CREATION) {
                    PostCreationScreenDestination(navController)
                }

                composable(route = NavigationKeys.Route.PROFILE) {
                    LoginRequired(authenticationViewModel, it) {
                        ProfileScreenDestination(navController, authenticationViewModel)
                    }
                }
                composable(route = NavigationKeys.Route.USER_SEARCH) {
                    LoginRequired(authenticationViewModel, it) {
                        UserSearchScreenDestination(navController)
                    }
                }
                composable(route = NavigationKeys.Route.FRIENDS) {
                    LoginRequired(authenticationViewModel, it) {
                        FriendsScreenDestination(navController)
                    }
                }
                composable(route = NavigationKeys.Route.FRIEND_REQUEST_LIST) {
                    LoginRequired(authenticationViewModel, it) {
                        FriendRequestListDestination(navController)
                    }
                }
                composable(route = NavigationKeys.Route.MESSAGES) {
                    LoginRequired(authenticationViewModel, it) {
                        MessagesScreenDestination(navController, authenticationViewModel)
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
                    ProfileScreenDestination(
                        navController = navController,
                        authenticationViewModel = authenticationViewModel
                    )
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
            navController.navigate(NavigationKeys.Route.POST_CREATION)
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
        viewModel.state.myProfile = true
    }
    val context = LocalContext.current
    viewModel.userPosts?.let {
        ProfileScreen(
            state = viewModel.state,
            viewModel = viewModel,
            logout = {
                authenticationViewModel.doLogout(context)
            },
            onPressBack = {
                navController.popBackStack()
            },
            userPosts = it,
            onOpenAsset = {
                openFullScreenImagePage(it, navController)
            }
        )
    }
}

@Composable
private fun UserSearchScreenDestination(navController: NavController) {
    val viewModel: UserSearchScreenViewModel = hiltViewModel()

    UserSearchScreen(
        userList = viewModel.users,
        onUserClick = {
            navController.navigate(NavigationKeys.Route.PROFILE + "/" + it.id)
        },
        onPressBack = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun FriendsScreenDestination(navController: NavController) {
    val viewModel: FriendsScreenViewModel = hiltViewModel()
    val friendRequestListViewModel: FriendRequestListViewModel = hiltViewModel()

    FriendsScreen(
        userList = viewModel.friends,
        onFriendClick = {
            navController.navigate(NavigationKeys.Route.MESSAGES_ROOT + "/" + it.friendshipId)
        },
        friendRequestList = friendRequestListViewModel.friendRequestList,
        onSeeFriendRequests = {
            navController.navigate(NavigationKeys.Route.FRIEND_REQUEST_LIST)
        },
        onSearch = {
            navController.navigate(NavigationKeys.Route.USER_SEARCH)
        }
    )
}

@Composable
private fun FriendRequestListDestination(navController: NavController) {
    val viewModel: FriendRequestListViewModel = hiltViewModel()
    FriendRequestListScreen(
        friendRequestList = viewModel.friendRequestList,
        onPressBack = {
            navController.popBackStack()
        },
        onShowProfile = {
            navController.navigate(NavigationKeys.Route.PROFILE + "/" + it.id)
        },
        onFriendRequestStatusChange = { fr, status ->
            viewModel.changeFriendRequestStatus(fr, status)
        },
        toastMessage = viewModel.toastMessage
    )
}

@Composable
private fun MessagesScreenDestination(navController: NavController, authenticationViewModel: AuthenticationViewModel) {
    val viewModel: MessageScreenViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toast
            .collect { message ->
                Toast.makeText(
                    context,
                    message.messageId,
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }

    MessageScreen(
        messages = viewModel.messages,
        sendMessage = {
            viewModel.postMessage(it)
        },
        friendship = viewModel.friendship,
        currentUser = authenticationViewModel.profile.value!!,
        onPressBack = {
            navController.popBackStack()
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
        const val FRIENDSHIP_ID = "friendshipId"
    }


    object Route {
        const val PROFILE = "profile"
        const val USER_PROFILE = "profile/{$PROFILE_ID}"
        const val FEED = "feed"
        const val FRIENDS = "friends"
        const val FULL_SCREEN_IMAGE_ROUTE = "FullScreenImage?image={$IMAGE_URL}"
        const val FULL_SCREEN_IMAGE = "FullScreenImage?image="
        const val POST_CREATION = "createPost"
        const val FRIEND_REQUEST_LIST = "friendRequestScreen"
        const val USER_SEARCH = "userSearch"
        const val MESSAGES_ROOT = "messages"
        const val MESSAGES = "messages/{$FRIENDSHIP_ID}"

    }
}


