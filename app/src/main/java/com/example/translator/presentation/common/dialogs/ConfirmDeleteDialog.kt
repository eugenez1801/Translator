package com.example.translator.presentation.common.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmDeleteDialog(
    text: String,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    AlertDialog(
        title = {
            Text(
                text = text,
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
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Да",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancelClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = "Нет",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    )
}