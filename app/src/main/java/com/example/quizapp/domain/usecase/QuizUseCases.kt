package com.example.quizapp.domain.usecase

data class QuizUseCases(
    val initializeQuiz: InitializeQuizUseCase,
    val getQuizSession: GetQuizSessionUseCase,
    val submitAnswer: SubmitAnswerUseCase,
    val skipQuestion: SkipQuestionUseCase,
    val moveToNextQuestion: MoveToNextQuestionUseCase,
    val hasNextQuestion: HasNextQuestionUseCase,
    val getQuizResult: GetQuizResultUseCase,
)