package com.example.quizapp.di

import android.content.Context
import com.example.quizapp.data.network.AndroidConnectivityChecker
import com.example.quizapp.data.network.ConnectivityChecker
import com.example.quizapp.data.remote.QuizApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideConnectivityChecker(
        @ApplicationContext context: Context
    ): ConnectivityChecker {
        return AndroidConnectivityChecker(context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(
                "https://gist.githubusercontent.com/"
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideQuizApi(
        retrofit: Retrofit
    ): QuizApi {

        return retrofit.create(
            QuizApi::class.java
        )
    }
}