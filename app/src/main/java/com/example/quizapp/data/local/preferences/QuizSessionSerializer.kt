package com.example.quizapp.data.local.preferences

import androidx.datastore.core.Serializer
import com.example.quizapp.domain.model.QuizSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object QuizSessionSerializer : Serializer<QuizSession?> {

    override val defaultValue: QuizSession? = null

    override suspend fun readFrom(
        input: InputStream
    ): QuizSession? {

        return try {
            Json.decodeFromString<QuizSession>(
                input.readBytes().decodeToString()
            )
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun writeTo(
        t: QuizSession?,
        output: OutputStream
    ) {

        if (t == null) return

        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(t)
                    .encodeToByteArray()
            )
        }
    }
}