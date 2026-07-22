package com.example.quizapp.presentation.viewmodels

sealed interface QuizEvent {
    data object NavigateToResult : QuizEvent
}