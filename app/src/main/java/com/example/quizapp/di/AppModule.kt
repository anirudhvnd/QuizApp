package com.example.quizapp.di

import android.content.Context
import com.example.quizapp.data.local.assets.QuestionJsonParser
import com.example.quizapp.data.repository.QuizRepositoryImpl
import com.example.quizapp.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuestionJsonParser(
        @ApplicationContext context: Context
    ): QuestionJsonParser {
        return QuestionJsonParser(context)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(
        parser: QuestionJsonParser
    ): QuizRepository {
        return QuizRepositoryImpl(parser)
    }
}