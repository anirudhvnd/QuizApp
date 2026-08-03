package com.example.quizapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizSession(
    val questions: List<Question>,
    val currentQuestionIndex: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedQuestions: Int,
    val currentStreak: Int,
    val longestStreak: Int,
    val questionStatuses: List<QuestionStatus>,
    val hasStarted: Boolean = false,
    val hasAnswered: Boolean = false,
)