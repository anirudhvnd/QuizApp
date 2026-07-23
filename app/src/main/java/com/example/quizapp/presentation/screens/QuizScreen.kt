package com.example.quizapp.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quizapp.R
import com.example.quizapp.domain.model.Question
import com.example.quizapp.domain.model.QuizSession
import com.example.quizapp.presentation.screens.components.ErrorScreen
import com.example.quizapp.presentation.screens.components.LoadingScreen
import com.example.quizapp.presentation.screens.components.OptionButton
import com.example.quizapp.presentation.screens.components.QuestionCard
import com.example.quizapp.presentation.screens.components.QuizProgressIndicator
import com.example.quizapp.presentation.state.QuizUiState
import com.example.quizapp.presentation.viewmodels.QuizEvent
import com.example.quizapp.presentation.viewmodels.QuizViewModel
import com.example.quizapp.ui.theme.Accent
import com.example.quizapp.ui.theme.Correct
import com.example.quizapp.ui.theme.Wrong

@Composable
fun QuizScreen(
    onNavigateToResult: () -> Unit, viewModel: QuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                QuizEvent.NavigateToResult -> onNavigateToResult()
            }
        }
    }

    when (val state = uiState) {
        QuizUiState.Loading -> LoadingScreen()
        is QuizUiState.Success -> QuizContent(
            uiState = state,
            session = state.session,
            question = state.session.questions[state.session.currentQuestionIndex],
            onAnswerClick = viewModel::onAnswerSelected,
            onSkipClick = viewModel::onSkip
        )

        QuizUiState.Error -> ErrorScreen(
            onRetry = { viewModel.loadQuiz() }
        )
    }
}

@Composable
private fun QuizContent(
    uiState: QuizUiState.Success,
    session: QuizSession,
    question: Question,
    onAnswerClick: (Int) -> Unit,
    onSkipClick: () -> Unit
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background, bottomBar = {
            if (uiState.showAnswerOverlay) {

                val answerResult = requireNotNull(uiState.answerResult)

                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically { it } + fadeIn(),
                    exit = slideOutVertically { it } + fadeOut()) {

                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        shape = RoundedCornerShape(24.dp)
                    ) {

                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Surface(
                                modifier = Modifier.size(52.dp),
                                shape = CircleShape,
                                color = if (answerResult.isCorrect) Correct.copy(alpha = .15f)
                                else Wrong.copy(alpha = .15f)
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text = if (answerResult.isCorrect) "✓" else "✕",
                                        color = if (answerResult.isCorrect) Correct
                                        else Wrong,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    )
                                }
                            }


                            Spacer(Modifier.width(16.dp))


                            Column {

                                Text(
                                    text = if (answerResult.isCorrect) stringResource(R.string.correct_feedback)
                                    else stringResource(R.string.wrong_feedback),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = if (answerResult.isCorrect) stringResource(R.string.next_question_hint)
                                    else stringResource(R.string.wrong_answer_hint),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = !uiState.showAnswerOverlay
        ) {

            item { Spacer(Modifier.height(20.dp)) }

            item {

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {

                        Text(
                            stringResource(R.string.quiz_master),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            stringResource(
                                R.string.question_progress,
                                session.currentQuestionIndex + 1,
                                session.questions.size
                            ), style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    AnimatedVisibility(
                        visible = session.currentStreak >= 3,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {

                        val infiniteTransition = rememberInfiniteTransition()

                        val pulseScale = infiniteTransition.animateFloat(
                            initialValue = 1f,
                            targetValue = 1.08f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(700), repeatMode = RepeatMode.Reverse
                            )
                        )

                        Surface(
                            modifier = Modifier.scale(pulseScale.value),
                            shape = RoundedCornerShape(50),
                            color = Accent.copy(alpha = 0.15f)
                        ) {

                            Row(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp, vertical = 8.dp
                                ), verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(text = "🔥")

                                Spacer(Modifier.width(4.dp))

                                AnimatedContent(
                                    targetState = session.currentStreak, label = "streak_count"
                                ) { streak ->

                                    Text(
                                        text = "$streak", fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(20.dp)) }

            item {
                QuizProgressIndicator(
                    statuses = session.questionStatuses, currentIndex = session.currentQuestionIndex
                )
            }

            item { Spacer(Modifier.height(28.dp)) }

            item {
                QuestionCard(question.question)
            }

            item { Spacer(Modifier.height(28.dp)) }

            itemsIndexed(question.options) { index, option ->
                OptionButton(
                    text = option,
                    optionLetter = ('A' + index).toString(),
                    enabled = !uiState.showAnswerOverlay,
                    isSelected = uiState.answerResult?.selectedOptionIndex == index,
                    isCorrect = uiState.answerResult?.correctOptionIndex == index,
                    onClick = { onAnswerClick(index) })
                Spacer(Modifier.height(14.dp))
            }

            item {

                TextButton(
                    onClick = onSkipClick,
                    enabled = !uiState.showAnswerOverlay,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(
                        stringResource(R.string.skip_question_button),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}
