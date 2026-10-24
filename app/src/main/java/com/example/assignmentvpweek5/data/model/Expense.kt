package com.example.assignmentvpweek5.data.model

import java.util.UUID

data class Expense(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val amount: Double
)