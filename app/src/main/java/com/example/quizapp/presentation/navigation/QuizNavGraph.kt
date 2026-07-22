package com.example.quizapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.presentation.screens.QuizScreen
import com.example.quizapp.presentation.screens.ResultScreen

@Composable
fun QuizNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Quiz
    ) {
        composable<Quiz> {
            QuizScreen(
                onNavigateToResult = {
                    navController.navigate(Result) {
                        popUpTo<Quiz> { inclusive = true }
                    }
                }
            )
        }

        composable<Result> {
            ResultScreen(
                onRestart = {
                    navController.navigate(Quiz) {
                        popUpTo<Result> { inclusive = true }
                    }
                }
            )
        }
    }
}