package com.example.assignmentvpweek5.ui.view.expense

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignmentvpweek5.ui.view.expense.components.ExpenseItem
import com.example.assignmentvpweek5.ui.viewmodel.ExpenseViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ExpenseScreen(vm: ExpenseViewModel) {
    val items by vm.items.collectAsState()
    val err by vm.err.collectAsState()
    val editId by vm.activeId.collectAsState()

    var name by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(editId) {
        if (editId != null) {
            name = vm.inputName.value
            amount = vm.inputAmount.value
        }
    }

    val fmt = NumberFormat.getCurrencyInstance(Locale("id", "ID")).apply {
        maximumFractionDigits = 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Expenses", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(
            "Total Expense : ${fmt.format(vm.getTotal())}",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                vm.onNameChanged(it)
            },
            label = { Text("Expense Name") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                    vm.onAmountChanged(it)
                },
                label = { Text("Amount of Expense") },
                isError = err != null,
                supportingText = { if (err != null) Text(err!!) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    vm.saveExpense()
                    if (vm.err.value == null) {
                        name = ""
                        amount = ""
                    }
                },
                modifier = Modifier.padding(bottom = if (err != null) 16.dp else 0.dp)
            ) {
                Icon(
                    imageVector = if (editId != null) Icons.Default.Check else Icons.Default.Add,
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(items, key = { it.id }) { item ->
                ExpenseItem(
                    item = item,
                    fmt = fmt,
                    onDelete = { vm.removeExpense(item.id) },
                    onEdit = {
                        vm.editExpense(item)
                        name = item.name
                        amount = item.amount.toLong().toString()
                    }
                )
            }
        }
    }
}