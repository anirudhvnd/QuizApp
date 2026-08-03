package com.example.quizapp.data.repository

import com.example.quizapp.data.local.preferences.QuizSessionDataSource
import com.example.quizapp.data.mapper.toDomain
import com.example.quizapp.data.network.ConnectivityChecker
import com.example.quizapp.data.network.NoInternetException
import com.example.quizapp.data.remote.QuizApi
import com.example.quizapp.domain.model.AnswerResult
import com.example.quizapp.domain.model.QuestionStatus
import com.example.quizapp.domain.model.QuizLoadResult
import com.example.quizapp.domain.model.QuizResult
import com.example.quizapp.domain.model.QuizSession
import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val api: QuizApi,
    private val dataStore: QuizSessionDataSource,
    private val connectivityChecker: ConnectivityChecker
) : QuizRepository {


    override suspend fun initializeQuiz(): Result<QuizLoadResult> {

        return runCatching {

            // Resume existing quiz
            val existingSession = dataStore.getSession()
            if (existingSession != null && existingSession.hasStarted) {
                val resumedSession =
                    if (existingSession.hasAnswered && hasNextQuestion()) {
                        val updatedSession = existingSession.copy(
                            currentQuestionIndex =
                                existingSession.currentQuestionIndex + 1,
                            hasAnswered = false
                        )
                        dataStore.saveSession(updatedSession)

                        updatedSession
                    } else {
                        existingSession
                    }

                return@runCatching QuizLoadResult(
                    session = resumedSession,
                    isResumed = true
                )
            }
            if (!connectivityChecker.isConnected()) {
                throw NoInternetException()
            }
            val questions = api.getQuestions()
                .map { it.toDomain() }

            val session = QuizSession(
                questions = questions,
                currentQuestionIndex = 0,
                correctAnswers = 0,
                wrongAnswers = 0,
                skippedQuestions = 0,
                currentStreak = 0,
                longestStreak = 0,
                hasStarted = false,
                hasAnswered = false,
                questionStatuses = List(questions.size) {
                    QuestionStatus.UNANSWERED
                }
            )

            dataStore.saveSession(session)

            QuizLoadResult(
                session = session,
                isResumed = false
            )
        }
    }

    override suspend fun getQuizSession(): QuizSession? {
        return dataStore.getSession()
    }

    override suspend fun submitAnswer(
        selectedOptionIndex: Int
    ): AnswerResult {
        val session = requireNotNull(getQuizSession())

        val question =
            session.questions[session.currentQuestionIndex]

        val isCorrect =
            selectedOptionIndex == question.correctOptionIndex

        val updatedStatuses =
            session.questionStatuses.toMutableList()

        updatedStatuses[session.currentQuestionIndex] =
            if (isCorrect)
                QuestionStatus.CORRECT
            else
                QuestionStatus.WRONG

        val newStreak =
            if (isCorrect)
                session.currentStreak + 1
            else
                0

        val updatedSession = session.copy(
            hasStarted = true,
            hasAnswered = true,
            correctAnswers =
                if (isCorrect)
                    session.correctAnswers + 1
                else
                    session.correctAnswers,
            wrongAnswers =
                if (isCorrect)
                    session.wrongAnswers
                else
                    session.wrongAnswers + 1,
            currentStreak = newStreak,
            longestStreak =
                maxOf(
                    session.longestStreak,
                    newStreak
                ),
            questionStatuses = updatedStatuses
        )
        dataStore.saveSession(updatedSession)

        return AnswerResult(
            selectedOptionIndex = selectedOptionIndex,
            correctOptionIndex = question.correctOptionIndex,
            isCorrect = isCorrect
        )
    }

    override suspend fun skipQuestion() {
        val session = requireNotNull(getQuizSession())
        val updatedStatuses =
            session.questionStatuses.toMutableList()

        updatedStatuses[session.currentQuestionIndex] =
            QuestionStatus.SKIPPED

        dataStore.saveSession(
            session.copy(
                hasStarted = true,
                skippedQuestions = session.skippedQuestions + 1,
                currentStreak = 0,
                questionStatuses = updatedStatuses
            )
        )
    }

    override suspend fun moveToNextQuestion() {
        val session = requireNotNull(getQuizSession())
        if (hasNextQuestion()) {
            dataStore.saveSession(
                session.copy(
                    hasAnswered = false,
                    currentQuestionIndex =
                        session.currentQuestionIndex + 1
                )
            )
        }
    }

    override suspend fun hasNextQuestion(): Boolean {
        val session = requireNotNull(getQuizSession())
        return session.currentQuestionIndex <
                session.questions.lastIndex
    }

    override suspend fun getQuizResult(): QuizResult {
        val session = requireNotNull(getQuizSession())
        val percentage =
            (session.correctAnswers * 100) /
                    session.questions.size

        return QuizResult(
            correctAnswers = session.correctAnswers,
            wrongAnswers = session.wrongAnswers,
            skippedQuestions = session.skippedQuestions,
            longestStreak = session.longestStreak,
            percentage = percentage
        )
    }

     override suspend fun clearSession() {
        dataStore.clearSession()
    }
}