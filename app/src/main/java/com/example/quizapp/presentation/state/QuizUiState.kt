package com.example.quizapp.presentation.state

import com.example.quizapp.domain.model.AnswerResult
import com.example.quizapp.domain.model.QuizSession

sealed interface QuizUiState {

    data object Loading : QuizUiState

    data class Success(
        val session: QuizSession,
        val answerResult: AnswerResult? = null,
        val showAnswerOverlay: Boolean = false
    ) : QuizUiState

    data object Error : QuizUiState

}