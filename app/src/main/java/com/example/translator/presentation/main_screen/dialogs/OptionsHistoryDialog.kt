package com.example.translator.presentation.main_screen.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OptionsHistoryDialog(
    onClearHistoryClick: () -> Unit,
    onHideDialogClick: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Выберите действие")
        },
        text = {
            TextButton(
                onClick = { onClearHistoryClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Очистить историю поиска",
                    modifier = Modifier
                        .size(13.dp)
                        .padding(end = 3.dp)
                )
                Text(
                    text = "Очистить историю поиска",
                    fontSize = 16.sp
                )
            }
            /*Column {
                TextButton(
                    onClick = { onClearHistoryClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = "Очистить историю поиска",
                        modifier = Modifier
                            .size(13.dp)
                            .padding(end = 3.dp)
                    )
                    Text(
                        text = "Очистить историю поиска",
                        fontSize = 16.sp
                    )
                }
                HorizontalDivider(thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                )
            }*/
        },
        onDismissRequest = {
            onHideDialogClick()
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = {
                    onHideDialogClick()
                }
            ) {
                Text("Закрыть окно")
            }
        }
    )
}