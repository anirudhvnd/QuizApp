package com.example.quizapp.domain.usecase

import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class ClearQuizResultSession @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke() {
        return repository.clearSession()
    }
}