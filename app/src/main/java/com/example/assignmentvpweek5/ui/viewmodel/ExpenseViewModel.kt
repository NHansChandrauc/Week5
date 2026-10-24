package com.example.assignmentvpweek5.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.assignmentvpweek5.data.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExpenseViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<Expense>>(
        listOf(
            Expense(name = "Makan Nasi", amount = 90000.0),
            Expense(name = "Investasi", amount = 10000000.0),
            Expense(name = "Kopi", amount = 18000.0)
        )
    )
    val items = _items.asStateFlow()

    val inputName = MutableStateFlow("")
    val inputAmount = MutableStateFlow("")
    val err = MutableStateFlow<String?>(null)
    val activeId = MutableStateFlow<String?>(null)

    fun onNameChanged(txt: String) {
        inputName.value = txt
    }

    fun onAmountChanged(txt: String) {
        inputAmount.value = txt
        err.value = null
    }

    fun saveExpense() {
        val nameVal = inputName.value.trim()
        val numVal = inputAmount.value.toDoubleOrNull()

        if (nameVal.isEmpty()) {
            err.value = "Name required"
            return
        }
        if (numVal == null || numVal <= 0) {
            err.value = "Amount must be greater than 0"
            return
        }

        val currId = activeId.value
        if (currId != null) {
            _items.update { list ->
                list.map { if (it.id == currId) it.copy(name = nameVal, amount = numVal) else it }
            }
            activeId.value = null
        } else {
            _items.update { it + Expense(name = nameVal, amount = numVal) }
        }

        inputName.value = ""
        inputAmount.value = ""
        err.value = null
    }

    fun removeExpense(id: String) {
        _items.update { list -> list.filter { it.id != id } }
        if (activeId.value == id) {
            activeId.value = null
            inputName.value = ""
            inputAmount.value = ""
        }
    }

    fun editExpense(item: Expense) {
        activeId.value = item.id
        inputName.value = item.name
        inputAmount.value = item.amount.toLong().toString()
        err.value = null
    }

    fun getTotal(): Double {
        return _items.value.sumOf { it.amount }
    }
}