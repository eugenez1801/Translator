package com.example.translator.presentation.main_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemHistory(
    english: String,
    russian: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 5.dp)
    ) {
        Text(
            text = english,
            fontSize = 20.sp
        )

        Text(
            text = russian,
            fontSize = 20.sp,
            color = Color.Gray
        )
    }
}