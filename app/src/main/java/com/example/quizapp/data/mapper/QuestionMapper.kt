package com.example.quizapp.data.mapper

import com.example.quizapp.data.model.QuestionDto
import com.example.quizapp.domain.model.Question

fun QuestionDto.toDomain() = Question(
    id = id,
    question = question,
    options = options,
    correctOptionIndex = correctOptionIndex
)