package com.example.translator.presentation.main_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchPart(
    textInSearchField: String,
    onTextChange: (String) -> Unit,
    requestText: String,
    resultText: String,
    onSearchClick: () -> Unit,
    onEraseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = textInSearchField,
                onValueChange = {
                    onTextChange(it)
                },
                label = {
                    Text("Введите слово для перевода")
                },
                trailingIcon = {
                    if (textInSearchField.isNotBlank()){
                        IconButton(
                            onClick = {
                                onEraseClick()
                            },
                            modifier = Modifier.padding(top = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Очистить поле",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.86f),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSearchClick()
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White
                )
            )

            IconButton(
                onClick = {
                    onSearchClick()
                },
                modifier = Modifier.padding(top = 5.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Поиск",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        Text(
            text = requestText
        )

        Text(
            text = resultText
        )
    }
}