package com.example.assignmentvpweek5.ui.view.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignmentvpweek5.ui.viewmodel.QuizViewModel

@Composable
fun QuizScreen(vm: QuizViewModel) {
    val idx by vm.currentIdx.collectAsState()
    val selected by vm.selectedIdx.collectAsState()
    val q = vm.getCurrQuestion()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Question ${idx + 1}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(q.title, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(32.dp))

        q.options.forEachIndexed { i, opt ->
            val isSel = selected == i
            Button(
                onClick = { vm.selectedIdx.value = i },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSel) MaterialTheme.colorScheme.primary else Color(0xFFEAEFFE),
                    contentColor = if (isSel) Color.White else Color.Black
                )
            ) {
                Text(opt)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { vm.next() },
            enabled = selected != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}