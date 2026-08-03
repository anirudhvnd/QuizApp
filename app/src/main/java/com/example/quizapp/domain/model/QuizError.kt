package com.example.quizapp.domain.model

sealed interface QuizError {

    data object NoInternet : QuizError

    data object ServerError : QuizError

    data class Unknown(
        val message: String
    ) : QuizError
}