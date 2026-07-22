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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCases: QuizUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<QuizEvent>()
    val event = _event.asSharedFlow()

    init {
        useCases.initializeQuiz()

        _uiState.update {
            it.copy(
                isLoading = false, session = useCases.getQuizSession()
            )
        }
    }

    fun onAnswerSelected(index: Int) {

        val answer = useCases.submitAnswer(index)

        _uiState.update {
            it.copy(
                answerResult = answer, showAnswerOverlay = true, session = useCases.getQuizSession()
            )
        }

        viewModelScope.launch {

            delay(2000)

            if (useCases.hasNextQuestion()) {
                useCases.moveToNextQuestion()

                _uiState.update {
                    it.copy(
                        session = useCases.getQuizSession(),
                        answerResult = null,
                        showAnswerOverlay = false
                    )
                }
            } else {
                _event.emit(QuizEvent.NavigateToResult)
            }
        }
    }

    fun onSkip() {

        useCases.skipQuestion()

        if (useCases.hasNextQuestion()) {

            useCases.moveToNextQuestion()

            _uiState.update {
                it.copy(
                    session = useCases.getQuizSession()
                )
            }
        } else {
            viewModelScope.launch { _event.emit(QuizEvent.NavigateToResult) }
        }
    }
}