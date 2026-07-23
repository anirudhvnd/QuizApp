package com.example.quizapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.usecase.QuizUseCases
import com.example.quizapp.presentation.state.QuizUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCases: QuizUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<QuizEvent>()
    val event = _event.asSharedFlow()

    init {
        loadQuiz()
    }

    fun loadQuiz() {
        useCases.initializeQuiz().onSuccess {
            _uiState.value = QuizUiState.Success(
                session = useCases.getQuizSession(),
            )
        }.onFailure {
            _uiState.value = QuizUiState.Error
        }
    }

    fun onAnswerSelected(index: Int) {

        val state = _uiState.value as? QuizUiState.Success ?: return
        val answer = useCases.submitAnswer(index)

        _uiState.value = state.copy(
            session = useCases.getQuizSession(), answerResult = answer, showAnswerOverlay = true
        )

        viewModelScope.launch {

            delay(2000)
            if (useCases.hasNextQuestion()) {
                useCases.moveToNextQuestion()
                val current = _uiState.value as? QuizUiState.Success ?: return@launch
                _uiState.value = current.copy(
                    session = useCases.getQuizSession(),
                    answerResult = null,
                    showAnswerOverlay = false
                )
            } else {
                _event.emit(QuizEvent.NavigateToResult)
            }
        }
    }

    fun onSkip() {
        val state = _uiState.value as? QuizUiState.Success ?: return
        useCases.skipQuestion()
        if (useCases.hasNextQuestion()) {
            useCases.moveToNextQuestion()
            _uiState.value = state.copy(
                session = useCases.getQuizSession()
            )
        } else {
            viewModelScope.launch {
                _event.emit(QuizEvent.NavigateToResult)
            }
        }
    }
}