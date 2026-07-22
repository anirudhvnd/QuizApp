package com.example.quizapp.presentation.state

import com.example.quizapp.domain.model.AnswerResult
import com.example.quizapp.domain.model.QuizSession

data class QuizUiState(
    val isLoading: Boolean = true,
    val session: QuizSession? = null,
    val answerResult: AnswerResult? = null,
    val showAnswerOverlay: Boolean = false
)