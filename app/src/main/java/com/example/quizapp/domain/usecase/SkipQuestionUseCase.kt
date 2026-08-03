package com.example.quizapp.domain.usecase

import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class SkipQuestionUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke() {
        return repository.skipQuestion()
    }
}