package com.example.quizapp.domain.usecase

import com.example.quizapp.domain.model.QuizResult
import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class GetQuizResultUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    operator fun invoke(): QuizResult {
        return repository.getQuizResult()
    }
}