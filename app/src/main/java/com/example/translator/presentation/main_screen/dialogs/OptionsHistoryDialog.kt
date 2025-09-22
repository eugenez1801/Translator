package com.example.translator.presentation.main_screen.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.translator.presentation.common.DefaultRadioButton

@Composable
fun OptionsHistoryDialog(
    orderNew: Boolean,
    onChangeOrderClick: (Boolean) -> Unit,
    onClearHistoryClick: () -> Unit,
    onHideDialogClick: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Опции")
        },
        text = {
            Column() {
                TextButton(
                    onClick = { onClearHistoryClick() },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "Очистить историю поиска",
                            modifier = Modifier
                                .size(15.dp)
                                .padding(end = 3.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Очистить историю поиска",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }

                Text(
                    text = "Сортировка запросов",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DefaultRadioButton(
                        text = "Сначала новые",
                        selected = orderNew,
                        onSelect = { onChangeOrderClick(true) }
                    )

                    DefaultRadioButton(
                        text = "Сначала старые",
                        selected = !orderNew,
                        onSelect = { onChangeOrderClick(false) }
                    )
                }
            }
        },
        onDismissRequest = {
            onHideDialogClick()
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = {
                    onHideDialogClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = "Закрыть окно",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
    )
}