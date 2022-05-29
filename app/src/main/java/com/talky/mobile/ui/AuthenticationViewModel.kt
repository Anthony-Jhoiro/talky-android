package com.talky.mobile.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.result.UserProfile
import com.talky.mobile.api.services.TalkyUsersService
import com.talky.mobile.api.models.CreateUserRequestDto
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.facades.AuthenticationFacade
import com.talky.mobile.api.services.PingService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val userApi: TalkyUsersService,
    private val authenticationFacade: AuthenticationFacade,
    @ApplicationContext private val context: Context,
    private val pingService: PingService
) : AndroidViewModel(context as Application) {

    var profile: MutableState<UserDto?> = mutableStateOf(null)

    var isLoggedIn = mutableStateOf(false)

    var isFetching = mutableStateOf(true)
    private var pingRoutineIndex = mutableStateOf(0)

    init {
        authenticationFacade.loadAuthentication(
            context,
            successListener = {
                reloadState {
                    isFetching.value = false
                }
            },
            failureListener = {
                reloadState {
                    isFetching.value = false
                }
            }
        )
    }

    private fun reloadState(callback: () -> Unit = {}) {
        viewModelScope.launch {
            profile.value = userApi.getProfile()
            isLoggedIn.value = authenticationFacade.isLoggedIn(context)
            callback()
            resetPingRoutine()
        }
    }

    private fun startPingRoutine() {
        val index = pingRoutineIndex.value
        viewModelScope.launch {
            if (isLoggedIn.value) {
                while (index == pingRoutineIndex.value) {
                    pingApi()
                    delay(getPingDelay())
                }
            }
        }
    }

    private fun getPingDelay(): Long {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context.registerReceiver(null, ifilter)
        }

        val batteryPct: Float? = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        }

        if (batteryPct != null) {
            if (batteryPct > 50) {
                // Battery High
                return 3 * 60 * 1000L
            } else if (batteryPct > 20) {
                // Battery Medium-low
                return 5 * 60 * 1000L
            } else {
                // Battery Low
                return 10 * 60 * 1000L
            }
        }
        return 5 * 60 * 1000L
    }

    private fun resetPingRoutine() {
        pingRoutineIndex.value += 1
        startPingRoutine()
    }

    private suspend fun pingApi() {
        if (isLoggedIn.value) {
            pingService.sendPingWithDevice()
        }
    }

    fun doLogin(context: Context) {
        viewModelScope.launch {
            authenticationFacade.loginUser(
                context,
                failureListener = {
                },
                successListener = {
                    if (profile.value == null) {
                        loadAuthProfile()
                    }
                }
            )
        }
    }

    private fun loadAuthProfile() {
        viewModelScope.launch {
            authenticationFacade.loadProfile(
                context,
                successListener = { auth0Data ->
                    createProfile(auth0Data)
                },
                failureListener = {
                }
            )
        }
    }

    private fun createProfile(auth0Data: UserProfile) {
        val request = CreateUserRequestDto(
            displayedName = auth0Data.givenName,
            defaultProfilePicture = auth0Data.pictureURL
        )
        viewModelScope.launch {
            userApi.createProfile(request)
            reloadState()
        }
    }

    fun doLogout(context: Context) {
        viewModelScope.launch {
            authenticationFacade.logout(context) {
                userApi.reset()
                reloadState()
            }
        }
    }

}
