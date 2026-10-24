package com.example.assignmentvpweek5.data.model

data class QuizResult(
    val qNum: Int,
    val question: String,
    val userAns: String,
    val correctAns: String,
    val isCorrect: Boolean
)