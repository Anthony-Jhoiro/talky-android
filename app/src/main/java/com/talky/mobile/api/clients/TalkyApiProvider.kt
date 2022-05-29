package com.talky.mobile.api.clients

import android.content.Context
import com.talky.mobile.api.apis.*
import com.talky.mobile.api.infrastructure.Serializer
import com.talky.mobile.facades.AuthenticationFacade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class TalkyApiProvider {

    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(@ApplicationContext context: Context, authenticationFacade: AuthenticationFacade): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(object: Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    if (authenticationFacade.isLoggedIn(context)) {

                        val requestWithAuth: Request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer ${authenticationFacade.accessToken}")
                            .build()
                        return chain.proceed(requestWithAuth)

                    }

                    return chain.proceed(chain.request())
                }

            })
            .build()
    }

    @Provides
    @Singleton
    @Named("USERS")
    fun provideRetrofitUsers(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(USERS_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Serializer.gsonBuilder.create()))
            .build()
    }

    @Provides
    @Singleton
    @Named("POSTS")
    fun provideRetrofitPosts(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(POSTS_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Serializer.gsonBuilder.create()))
            .build()
    }

    @Provides
    @Singleton
    @Named("SOCIAL")
    fun provideRetrofitSocial(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(SOCIAL_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Serializer.gsonBuilder.create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideDeviceControllerApi(@Named("USERS") retrofit: Retrofit): DeviceControllerApi {
        return retrofit.create(DeviceControllerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFriendRequestControllerApi(@Named("SOCIAL") retrofit: Retrofit): FriendRequestControllerApi {
        return retrofit.create(FriendRequestControllerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFriendshipControllerApi(@Named("SOCIAL") retrofit: Retrofit): FriendshipControllerApi {
        return retrofit.create(FriendshipControllerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageControllerApi(@Named("SOCIAL") retrofit: Retrofit): MessageControllerApi {
        return retrofit.create(MessageControllerApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostControllerApi(@Named("POSTS") retrofit: Retrofit): PostControllerApi {
        return retrofit.create(PostControllerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserControllerApi(@Named("USERS") retrofit: Retrofit): UserControllerApi {
        return retrofit.create(UserControllerApi::class.java)
    }



    companion object {
        const val USERS_API_URL = "https://api.talky.jho.ovh/users/"
        const val POSTS_API_URL = "https://api.talky.jho.ovh/posts/"
        const val SOCIAL_API_URL = "https://api.talky.jho.ovh/social/"
    }


}