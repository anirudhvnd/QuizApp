package com.example.quizapp.data.repository

import com.example.quizapp.data.local.QuestionJsonParser
import com.example.quizapp.data.mapper.toDomain
import com.example.quizapp.domain.model.AnswerResult
import com.example.quizapp.domain.model.QuestionStatus
import com.example.quizapp.domain.model.QuizResult
import com.example.quizapp.domain.model.QuizSession
import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val parser: QuestionJsonParser
) : QuizRepository {

    private lateinit var session: QuizSession

    override fun initializeQuiz(): Result<Unit> {

        return runCatching {
            val questions = parser
                .loadQuestions()
                .map { it.toDomain() }

            session = QuizSession(
                questions = questions,
                currentQuestionIndex = 0,
                correctAnswers = 0,
                wrongAnswers = 0,
                skippedQuestions = 0,
                currentStreak = 0,
                longestStreak = 0,
                questionStatuses = List(questions.size) {
                    QuestionStatus.UNANSWERED
                }
            )
        }
    }

    override fun getQuizSession(): QuizSession = session

    override fun submitAnswer(
        selectedOptionIndex: Int
    ): AnswerResult {

        val question = session.questions[session.currentQuestionIndex]

        val isCorrect = selectedOptionIndex == question.correctOptionIndex

        val updatedStatuses = session.questionStatuses.toMutableList()

        updatedStatuses[session.currentQuestionIndex] =
            if (isCorrect) QuestionStatus.CORRECT
            else QuestionStatus.WRONG

        val newStreak =
            if (isCorrect) session.currentStreak + 1
            else 0

        session = session.copy(
            correctAnswers = if (isCorrect) session.correctAnswers + 1 else session.correctAnswers,

            wrongAnswers = if (isCorrect) session.wrongAnswers else session.wrongAnswers + 1,

            currentStreak = newStreak,

            longestStreak = maxOf(session.longestStreak, newStreak),

            questionStatuses = updatedStatuses
        )

        return AnswerResult(
            selectedOptionIndex = selectedOptionIndex,
            correctOptionIndex = question.correctOptionIndex,
            isCorrect = isCorrect
        )
    }

    override fun skipQuestion() {

        val updatedStatuses = session.questionStatuses.toMutableList()

        updatedStatuses[session.currentQuestionIndex] = QuestionStatus.SKIPPED

        session = session.copy(
            skippedQuestions = session.skippedQuestions + 1,
            currentStreak = 0,
            questionStatuses = updatedStatuses
        )
    }

    override fun moveToNextQuestion() {

        if (hasNextQuestion()) {
            session = session.copy(
                currentQuestionIndex = session.currentQuestionIndex + 1
            )
        }
    }

    override fun hasNextQuestion(): Boolean {
        return session.currentQuestionIndex < session.questions.lastIndex
    }

    override fun getQuizResult(): QuizResult {

        val percentage =
            (session.correctAnswers * 100) / session.questions.size

        return QuizResult(
            correctAnswers = session.correctAnswers,
            wrongAnswers = session.wrongAnswers,
            skippedQuestions = session.skippedQuestions,
            longestStreak = session.longestStreak,
            percentage = percentage
        )
    }
}