package com.example.quizapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.quizapp.domain.usecase.GetQuizResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    getQuizResultUseCase: GetQuizResultUseCase
) : ViewModel() {
    val result = getQuizResultUseCase.invoke()
}