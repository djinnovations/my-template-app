package com.djphy.myapptemplate.di

import android.content.Context
import androidx.annotation.NonNull
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object{
        const val BASE_URL = "http://api.spacex.land/graphql/"
    }


    @Singleton
    @Provides
    fun provideHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(300, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
//            .addNetworkInterceptor(ApiKeyRequestInterceptor())
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideApolloClient(@NonNull okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}