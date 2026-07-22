package com.example.quizapp.domain.model

data class AnswerResult(
    val isCorrect: Boolean,
    val correctOptionIndex: Int,
    val selectedOptionIndex: Int,
)