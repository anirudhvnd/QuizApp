package com.example.quizapp.domain.usecase

import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class InitializeQuizUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    operator fun invoke(): Result<Unit> {
        return repository.initializeQuiz()
    }
}