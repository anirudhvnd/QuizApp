package com.example.quizapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.model.QuizResult
import com.example.quizapp.domain.usecase.ClearQuizResultSession
import com.example.quizapp.domain.usecase.GetQuizResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getQuizResultUseCase: GetQuizResultUseCase,
    private val clearQuizResultSession: ClearQuizResultSession
) : ViewModel() {


    private val _result = MutableStateFlow<QuizResult?>(null)
    val result = _result.asStateFlow()


    init {
        viewModelScope.launch {
            _result.value = getQuizResultUseCase.invoke()
            clearSession()
        }
    }

    fun clearSession(){
        viewModelScope.launch {
            clearQuizResultSession.invoke()
        }
    }
}