package com.example.translator.presentation.favourites_screen.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemFavourites(
    position: Int,
    english: String,
    russian: String,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp)
            .clickable(
                onClick = { onDeleteClick() },
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = english,
            modifier = Modifier
                .weight(0.45f),
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )

        Text(
            text = "$position",
            modifier = Modifier
                .weight(0.1f),
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = russian,
            modifier = Modifier
                .weight(0.45f),
            fontSize = 16.sp,
            textAlign = TextAlign.End
        )
    }
}