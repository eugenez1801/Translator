package com.example.translator.presentation.favourites_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptocurrencyappcompose.presentation.coin_list.dialogs.search_dialog.components.DefaultRadioButton

@Composable
fun OptionSection(
    orderNew: Boolean,
    onChangeOrderClick: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Выберите вариант сортировки",
            modifier = Modifier

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
}