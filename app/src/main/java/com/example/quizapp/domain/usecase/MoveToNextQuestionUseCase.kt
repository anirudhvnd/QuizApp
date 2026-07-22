package com.example.quizapp.domain.usecase

import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class MoveToNextQuestionUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    operator fun invoke() {
        return repository.moveToNextQuestion()
    }
}