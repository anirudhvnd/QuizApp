package com.example.quizapp.domain.model

data class QuizLoadResult(
    val session: QuizSession,
    val isResumed: Boolean
)