package com.example.quizapp.data.local.preferences

import android.content.Context
import androidx.datastore.dataStore

val Context.quizSessionDataStore by dataStore(
    fileName = "quiz_session.json",
    serializer = QuizSessionSerializer
)