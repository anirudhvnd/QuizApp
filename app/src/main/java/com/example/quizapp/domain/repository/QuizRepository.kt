package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.AnswerResult
import com.example.quizapp.domain.model.QuizResult
import com.example.quizapp.domain.model.QuizSession

interface QuizRepository {

    suspend fun initializeQuiz(): Result<Unit>

    fun getQuizSession(): QuizSession

    fun submitAnswer(selectedOptionIndex: Int): AnswerResult

    fun skipQuestion()

    fun moveToNextQuestion()

    fun getQuizResult(): QuizResult

    fun hasNextQuestion(): Boolean
}