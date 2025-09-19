package com.example.translator.presentation.main_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            .padding(15.dp)
    ) {
        Text(
            text = "Введите слово на английском для получения перевода",
            modifier = Modifier
                .padding(bottom = 10.dp)
        )

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
                                contentDescription = "clear",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.9f),
                singleLine = true
            )

            IconButton(
                onClick = {
                    onSearchClick()
                },
                modifier = Modifier.padding(top = 5.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search",
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