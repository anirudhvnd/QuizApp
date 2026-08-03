package com.example.quizapp.data.network

interface ConnectivityChecker {
    fun isConnected(): Boolean
}