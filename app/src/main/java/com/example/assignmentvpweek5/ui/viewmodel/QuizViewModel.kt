package com.example.assignmentvpweek5.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.assignmentvpweek5.data.model.Question
import com.example.assignmentvpweek5.data.model.QuizResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class Page {
    START, PLAY, RESULT
}

class QuizViewModel : ViewModel() {

    private val qList = listOf(
        Question(1, "Ibu Kota Di Indonesia?", listOf("Surabaya", "Bandung", "Jakarta", "Medan"), 2),
        Question(2, "Ada berapa hari dalam seminggu?", listOf("5", "6", "7", "10"), 2),
        Question(3, "Mata Kuliah VP pakai apa?", listOf("Word", "Android Studio", "VS Code", "Excel"), 1),
        Question(4, "Apakah Mata Kuliah VP susah?", listOf("Sangat Sulit", "Sulit", "Biasa Saja", "Sangat Mudah"), 3),
        Question(5, "Natal dirayakan pada bulan?", listOf("Agustus", "Oktober", "November", "Desember"), 3)
    )

    var pageState = MutableStateFlow(Page.START)
    var countInput = MutableStateFlow("")
    var errorMsg = MutableStateFlow<String?>(null)

    private val _pickedQuestions = MutableStateFlow<List<Question>>(emptyList())
    var currentIdx = MutableStateFlow(0)
    var selectedIdx = MutableStateFlow<Int?>(null)
    var history = MutableStateFlow<List<QuizResult>>(emptyList())

    fun start() {
        val num = countInput.value.toIntOrNull()
        if (num == null || num !in 1..qList.size) {
            errorMsg.value = "Maximum is ${qList.size} questions"
            return
        }

        _pickedQuestions.value = qList.shuffled().take(num)
        currentIdx.value = 0
        selectedIdx.value = null
        history.value = emptyList()
        pageState.value = Page.START
        pageState.value = Page.PLAY
    }

    fun next() {
        val sel = selectedIdx.value ?: return
        val q = _pickedQuestions.value[currentIdx.value]

        val res = QuizResult(
            qNum = currentIdx.value + 1,
            question = q.title,
            userAns = q.options[sel],
            correctAns = q.options[q.answerIdx],
            isCorrect = sel == q.answerIdx
        )

        history.value = history.value + res
        selectedIdx.value = null

        if (currentIdx.value + 1 < _pickedQuestions.value.size) {
            currentIdx.value++
        } else {
            pageState.value = Page.RESULT
        }
    }

    fun getCurrQuestion(): Question = _pickedQuestions.value[currentIdx.value]
    fun getScore(): Int = history.value.count { it.isCorrect }
    fun getTotalCount(): Int = _pickedQuestions.value.size

    fun reset() {
        countInput.value = ""
        errorMsg.value = null
        pageState.value = Page.START
    }
}