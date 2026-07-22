package com.example.quizapp.domain.model

data class QuizResult(
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedQuestions: Int,
    val longestStreak: Int,
    val percentage: Int
)