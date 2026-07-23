package com.example.quizapp.data.local

import android.content.Context
import com.example.quizapp.data.model.QuestionDto
import kotlinx.serialization.json.Json
import javax.inject.Inject

class QuestionJsonParser @Inject constructor(
    private val context: Context
) {
    fun loadQuestions(): List<QuestionDto> {
        val json = context.assets.open("questions.json")
            .bufferedReader()
            .use { it.readText() }

        return Json.decodeFromString(json)
    }
}