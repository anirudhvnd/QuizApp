package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.Question

interface QuizRepository {

    fun loadQuestions(): List<Question>
}