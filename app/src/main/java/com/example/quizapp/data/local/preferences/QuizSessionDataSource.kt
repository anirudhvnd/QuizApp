package com.example.quizapp.data.local.preferences

import android.content.Context
import com.example.quizapp.domain.model.QuizSession
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class QuizSessionDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore =
        context.quizSessionDataStore

    suspend fun saveSession(
        session: QuizSession
    ) {
        dataStore.updateData { session }
    }

    suspend fun getSession(): QuizSession? {
        return dataStore.data.first()
    }

    suspend fun clearSession() {
        dataStore.updateData { null }
    }
}