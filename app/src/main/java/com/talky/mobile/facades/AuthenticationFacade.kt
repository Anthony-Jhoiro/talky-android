package com.talky.mobile.facades

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.BaseCredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationFacade @Inject constructor() {

    var accessToken: String? = null
    private var auth0: Auth0? = null

    fun loadAuthentication(context: Context, successListener: (Credentials) -> Unit, failureListener: (CredentialsManagerException) -> Unit) {
        if (accessToken == null) {
            return getStorageManager(context).getCredentials(
                callback = object : Callback<Credentials, CredentialsManagerException> {
                    override fun onFailure(error: CredentialsManagerException) {
                        failureListener(error)
                    }

                    override fun onSuccess(result: Credentials) {
                        accessToken = result.accessToken
                        successListener(result)
                    }
                }
            )
        }
    }

    fun isLoggedIn(context: Context): Boolean {
        return getStorageManager(context).hasValidCredentials()
    }

    private fun getAuth0(context: Context): Auth0 {
        if (auth0 == null) {
            auth0 = Auth0(context)
        }
        return auth0!!
    }

    private fun getStorageManager(context: Context): BaseCredentialsManager {
        return SecureCredentialsManager(
            context,
            AuthenticationAPIClient(getAuth0(context)),
            SharedPreferencesStorage(context)
        )
    }

    suspend fun loginUser(
        context: Context,
        successListener: (Credentials) -> Unit,
        failureListener: (Exception) -> Unit
    ) = withContext(Dispatchers.IO) {
        WebAuthProvider.login(getAuth0(context))
            .withScheme("demo")
            .withAudience("tester-api")
            .withScope("openid profile email offline_access")
            .start(
                context,
                object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure(error: AuthenticationException) {
                        failureListener.invoke(error)
                    }

                    override fun onSuccess(result: Credentials) {
                        getStorageManager(context).saveCredentials(result)
                        accessToken = result.accessToken
                        successListener.invoke(result)
                    }
                }
            )
    }

    suspend fun loadProfile(
        context: Context,
        successListener: (UserProfile) -> Unit,
        failureListener: (Exception) -> Unit
    ) = withContext(Dispatchers.IO) {
        AuthenticationAPIClient(getAuth0(context)).userInfo(accessToken!!)
            .start(object :
                Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    failureListener.invoke(error)
                }

                override fun onSuccess(result: UserProfile) {
                    successListener.invoke(result)
                }
            })
    }

    suspend fun logout(
        context: Context,
        callback: () -> Unit
    ) = withContext(Dispatchers.IO) {
        WebAuthProvider.logout(getAuth0(context))
            .withScheme("demo")
            .start(
                context,
                object : Callback<Void?, AuthenticationException> {
                    override fun onSuccess(result: Void?) {
                        getStorageManager(context).clearCredentials()
                        callback()
                    }
                    override fun onFailure(error: AuthenticationException) {
                    }
                }
            )
    }


}