package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.AnswerResult
import com.example.quizapp.domain.model.QuizLoadResult
import com.example.quizapp.domain.model.QuizResult
import com.example.quizapp.domain.model.QuizSession

interface QuizRepository {

    suspend fun initializeQuiz(): Result<QuizLoadResult>

    suspend fun getQuizSession(): QuizSession?

    suspend fun submitAnswer(selectedOptionIndex: Int): AnswerResult

    suspend fun skipQuestion()

    suspend fun moveToNextQuestion()

    suspend fun getQuizResult(): QuizResult

    suspend fun hasNextQuestion(): Boolean

    suspend fun clearSession()
}