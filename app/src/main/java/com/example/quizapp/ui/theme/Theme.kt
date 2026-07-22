package com.example.quizapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(

    primary = Accent,
    onPrimary = Color.White,

    primaryContainer = SurfaceAlt,
    onPrimaryContainer = TextPrimary,

    secondary = Accent,
    onSecondary = Color.White,

    secondaryContainer = SurfaceAlt,
    onSecondaryContainer = TextPrimary,

    tertiary = Accent,

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceAlt,
    onSurfaceVariant = TextSecondary,

    outline = Border,
    outlineVariant = Border,

    error = Wrong,
    onError = Color.White
)

@Composable
fun QuizAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}