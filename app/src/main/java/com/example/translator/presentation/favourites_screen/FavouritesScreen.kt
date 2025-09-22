package com.example.translator.presentation.favourites_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.translator.presentation.common.dialogs.ConfirmDeleteDialog
import com.example.translator.presentation.favourites_screen.components.ItemFavourites
import kotlinx.coroutines.launch

@Composable
fun FavouritesScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val favouriteWordsList = viewModel.favouriteWordsList.value

    val showConfirmFavouriteDeleteDialog = viewModel.showConfirmFavouriteDeleteDialog.value

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showButtonForScroll by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 6 }//изначально 2 поставил, были лаги
    }

    var navigationAvailable by remember {//чтобы не было багов из-за быстрых нажатий навигаций
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
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
                enabled = navigationAvailable,
                onClick = {
                    navigationAvailable = false
                    navController.navigate(Screen.MainScreen){
                        popUpTo(Screen.FavouritesScreen){
                            inclusive = true
                        }
                        popUpTo(Screen.MainScreen){
                            inclusive = true
                        }
                    }
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
                    .padding(horizontal = 8.dp),
                state = listState
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
                                text = "Общее количество слов: ${favouriteWordsList.size}",
                                fontSize = 16.sp
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
                            onDeleteClick = {
                                viewModel.changeCurrentFavouriteWordForDialog(word)
                                viewModel.confirmFavouriteDeleteDialogShow(true)
                            }
                        )

                        if (position + 1 == favouriteWordsList.size){
                            HorizontalDivider(thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                        if (position + 1 == favouriteWordsList.size && showButtonForScroll){
                            Spacer(
                                modifier = Modifier
                                    .height(57.dp)
                            )
                        }
                    }
                }
            }

            if (showButtonForScroll) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = FloatingActionButtonDefaults.smallShape
                ) {
                    Text("Перейти к началу")
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

    if (showConfirmFavouriteDeleteDialog){
        ConfirmDeleteDialog(
            text = "Вы уверены, что хотите удалить слово из избранных?",
            onConfirmClick = {
                viewModel.deleteFavouriteWordFromList()
                viewModel.confirmFavouriteDeleteDialogShow(false)
            },
            onCancelClick = {
                viewModel.confirmFavouriteDeleteDialogShow(false)
            }
        )
    }
}