package com.example.quizapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class QuestionStatus {
    UNANSWERED,
    CORRECT,
    WRONG,
    SKIPPED
}