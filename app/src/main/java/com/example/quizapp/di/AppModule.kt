package com.example.quizapp.di

import com.example.quizapp.data.network.ConnectivityChecker
import com.example.quizapp.data.remote.QuizApi
import com.example.quizapp.data.repository.QuizRepositoryImpl
import com.example.quizapp.domain.repository.QuizRepository
import com.example.quizapp.domain.usecase.GetQuizResultUseCase
import com.example.quizapp.domain.usecase.GetQuizSessionUseCase
import com.example.quizapp.domain.usecase.HasNextQuestionUseCase
import com.example.quizapp.domain.usecase.InitializeQuizUseCase
import com.example.quizapp.domain.usecase.MoveToNextQuestionUseCase
import com.example.quizapp.domain.usecase.QuizUseCases
import com.example.quizapp.domain.usecase.SkipQuestionUseCase
import com.example.quizapp.domain.usecase.SubmitAnswerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuizRepository(
        api: QuizApi,
        connectivityChecker: ConnectivityChecker
    ): QuizRepository {
        return QuizRepositoryImpl(api,connectivityChecker)
    }

    @Provides
    @Singleton
    fun provideQuizUseCases(
        initializeQuiz: InitializeQuizUseCase,
        getQuizSession: GetQuizSessionUseCase,
        submitAnswer: SubmitAnswerUseCase,
        skipQuestion: SkipQuestionUseCase,
        moveToNextQuestion: MoveToNextQuestionUseCase,
        hasNextQuestion: HasNextQuestionUseCase,
        getQuizResult: GetQuizResultUseCase
    ) = QuizUseCases(
        initializeQuiz,
        getQuizSession,
        submitAnswer,
        skipQuestion,
        moveToNextQuestion,
        hasNextQuestion,
        getQuizResult
    )

    @Provides
    @Singleton
    fun provideGetQuizResultUseCase(
        quizRepository: QuizRepository
    ) = GetQuizResultUseCase(quizRepository)
}