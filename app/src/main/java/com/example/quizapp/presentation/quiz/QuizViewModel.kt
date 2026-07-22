package com.example.quizapp.presentation.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.quizapp.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    init {
        Log.d("Quiz", repository.loadQuestions().size.toString())
    }
}