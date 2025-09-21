package com.example.translator.presentation.main_screen.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmDeleteDialog(
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    AlertDialog(
        title = {
            Text(
                text = "Вы уверены, что хотите удалить слово из истории?",
                fontSize = 20.sp
            )
        },
        text = {},
        onDismissRequest = {
            onCancelClick()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick()
                }
            ) {
                Text("Да")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancelClick()
                }
            ) {
                Text("Нет")
            }
        }
    )
}