package com.example.quizapp.domain.usecase

import com.example.quizapp.domain.model.QuizLoadResult
import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class InitializeQuizUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(): Result<QuizLoadResult> {
        return repository.initializeQuiz()
    }
}