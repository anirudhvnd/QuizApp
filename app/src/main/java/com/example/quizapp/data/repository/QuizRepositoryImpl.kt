package com.example.quizapp.data.repository

import com.example.quizapp.data.local.assets.QuestionJsonParser
import com.example.quizapp.data.mapper.toDomain
import com.example.quizapp.domain.model.Question
import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val parser: QuestionJsonParser
) : QuizRepository {

    override fun loadQuestions(): List<Question> {
        return parser
            .loadQuestions()
            .map { it.toDomain() }
    }
}