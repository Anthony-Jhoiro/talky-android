package com.talky.mobile.api.clients

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url
import javax.inject.Named
import javax.inject.Singleton


interface UploadApi {
    @PUT
    suspend fun uploadImage(@Url url: String?, @Body file: RequestBody?): Response<ResponseBody>
}

@InstallIn(SingletonComponent::class)
@Module
class DefaultApiProvider {
    @Provides
    @Singleton
    @Named("DEFAULT")
    fun provideRetrofitUsers(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://storage.googleapis.com")
            .build()
    }

    @Provides
    @Singleton
    fun provideDeviceControllerApi(@Named("DEFAULT") retrofit: Retrofit): UploadApi {
        return retrofit.create(UploadApi::class.java)
    }
}