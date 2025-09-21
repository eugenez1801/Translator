package com.example.translator.presentation.favourites_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.translator.R
import com.example.translator.presentation.MainViewModel
import com.example.translator.presentation.Screen
import com.example.translator.presentation.favourites_screen.components.ItemFavourites

@Composable
fun FavouritesScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val favouriteWordsList = viewModel.favouriteWordsList.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Избранные слова",
                fontSize = 28.sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )

            IconButton(
                onClick = {
                    navController.navigate(Screen.MainScreen)
                },
                modifier = Modifier
                    .size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Перейти к главному экрану",
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    if (favouriteWordsList.isNotEmpty()){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_uk),
                                contentDescription = "Флаг Великобритании",
                                tint = Color.Unspecified
                            )

                            Text(
                                text = "Общее количество слов: ${favouriteWordsList.size}"
                            )

                            Icon(
                                painter = painterResource(R.drawable.ic_russia),
                                contentDescription = "Флаг России",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }

                itemsIndexed(favouriteWordsList) { position, word ->
                    Column(
                    ) {
                        HorizontalDivider(thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .align(Alignment.CenterHorizontally)
                        )

                        ItemFavourites(
                            position = position + 1,
                            english = word.english,
                            russian = word.russian,
                            onFavouriteClick = {},
                        )

                        if (position + 1 == favouriteWordsList.size){
                            HorizontalDivider(thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }

    if (favouriteWordsList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Text(
                text = "Избранных слов нет",
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}