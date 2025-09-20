package com.example.translator.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemHistory(
    english: String,
    russian: String,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
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

        IconButton(
            onClick = {onDeleteClick()},
            modifier = Modifier
//                .size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete from history",
                modifier = Modifier
                    .size(50.dp)
                    .padding(top = 6.dp)
            )
        }
    }
}