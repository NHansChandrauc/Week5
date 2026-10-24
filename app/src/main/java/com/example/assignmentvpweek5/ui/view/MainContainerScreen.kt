package com.example.assignmentvpweek5.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignmentvpweek5.ui.view.expense.ExpenseScreen
import com.example.assignmentvpweek5.ui.view.quiz.*
import com.example.assignmentvpweek5.ui.viewmodel.*

@Composable
fun MainContainerScreen(
    expenseVm: ExpenseViewModel = viewModel(),
    quizVm: QuizViewModel = viewModel()
) {
    var tab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = tab == 0,
                    onClick = { tab = 0 },
                    label = { Text("Expenses") },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = tab == 1,
                    onClick = { tab = 1 },
                    label = { Text("Quiz") },
                    icon = { Icon(Icons.Default.List, contentDescription = null) }
                )
            }
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            if (tab == 0) {
                ExpenseScreen(vm = expenseVm)
            } else {
                val page by quizVm.pageState.collectAsState()
                when (page) {
                    Page.START -> StartQuizScreen(vm = quizVm)
                    Page.PLAY -> QuizScreen(vm = quizVm)
                    Page.RESULT -> ResultScreen(vm = quizVm)
                }
            }
        }
    }
}