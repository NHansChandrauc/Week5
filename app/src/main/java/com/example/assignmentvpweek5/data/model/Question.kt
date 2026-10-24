package com.example.assignmentvpweek5.data.model

data class Question(
    val id: Int,
    val title: String,
    val options: List<String>,
    val answerIdx: Int
)