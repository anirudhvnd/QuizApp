package com.example.quizapp.domain.usecase

import com.example.quizapp.domain.model.QuizSession
import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class GetQuizSessionUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(): QuizSession? {
        return repository.getQuizSession()
    }
}