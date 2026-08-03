package com.example.quizapp.data.remote

import com.example.quizapp.data.model.QuestionDto
import retrofit2.http.GET

interface QuizApi {
    @GET("dr-samrat/53846277a8fcb034e482906ccc0d12b2/raw")
    suspend fun getQuestions(): List<QuestionDto>
}