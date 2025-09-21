package com.example.translator.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemHistory(
    position: Int,
    english: String,
    russian: String,
    isFavouriteWord: Boolean,
    onDeleteClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
                .align(Alignment.CenterVertically)
//                .border(1.dp, color = Color.Blue)
        ){
            Text(
                text = "${position + 1}",
                fontSize = if (position + 1 < 100) 30.sp
                    else 23.sp,// <1000 помещается при 23, при 24 уже нет
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 5.dp, horizontal = 10.dp)
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
            onClick = {
                onFavouriteClick()
            },
            modifier = Modifier
//                .size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                tint = if (isFavouriteWord) Color.Yellow
                    else Color.Black,
                contentDescription = "Make favourite",
                modifier = Modifier
                    .size(50.dp)
                    .padding(top = 6.dp)
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