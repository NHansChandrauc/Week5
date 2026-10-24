package com.example.assignmentvpweek5.ui.view.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignmentvpweek5.ui.viewmodel.QuizViewModel

@Composable
fun ResultScreen(vm: QuizViewModel) {
    val history by vm.history.collectAsState()
    val score = vm.getScore()
    val total = vm.getTotalCount()
    val wrong = history.filter { !it.isCorrect }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text("Quiz Finished!", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text("Your Score: $score / $total", fontSize = 18.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        if (wrong.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Perfect! No wrong answers.", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(wrong) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Q${item.qNum}: ${item.question}", fontWeight = FontWeight.SemiBold)
                        Text("Your Answer: ${item.userAns}", color = Color.Red, fontSize = 14.sp)
                        Text("Correct Answer: ${item.correctAns}", color = Color(0xFF2E7D32), fontSize = 14.sp)
                        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }

        Button(
            onClick = { vm.reset() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Restart Quiz")
        }
    }
}