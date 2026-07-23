package com.example.quizapp.presentation.screens.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quizapp.domain.model.QuestionStatus
import com.example.quizapp.ui.theme.Correct
import com.example.quizapp.ui.theme.SurfaceAlt
import com.example.quizapp.ui.theme.Wrong

@Composable
fun QuizProgressIndicator(
    statuses: List<QuestionStatus>,
    currentIndex: Int
) {
    Row(
        modifier = Modifier.width(340.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        statuses.forEachIndexed { index, status ->

            val scale by animateFloatAsState(
                targetValue = if (index == currentIndex) 1.15f else 1f,
                label = "progress_scale"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .scale(scale)
                    .background(
                        color = when(status) {

                            QuestionStatus.CORRECT ->
                                Correct

                            QuestionStatus.WRONG ->
                                Wrong

                            QuestionStatus.SKIPPED ->
                                Color(0xFFF59E0B)

                            QuestionStatus.UNANSWERED ->
                                SurfaceAlt
                        },
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}