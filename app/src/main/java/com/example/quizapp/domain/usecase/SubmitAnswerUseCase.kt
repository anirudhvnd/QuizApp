package com.example.quizapp.domain.usecase

import com.example.quizapp.domain.model.AnswerResult
import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class SubmitAnswerUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(selectedIndex: Int): AnswerResult {
        return repository.submitAnswer(selectedIndex)
    }
}