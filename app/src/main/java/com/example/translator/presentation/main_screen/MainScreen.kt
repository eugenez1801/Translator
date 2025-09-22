package com.example.translator.presentation.main_screen

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.translator.R
import com.example.translator.presentation.MainViewModel
import com.example.translator.presentation.Screen
import com.example.translator.presentation.main_screen.components.ItemHistory
import com.example.translator.presentation.main_screen.components.SearchPart
import com.example.translator.presentation.common.dialogs.ConfirmDeleteDialog
import com.example.translator.presentation.main_screen.dialogs.OptionsHistoryDialog
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val searchPartState = viewModel.searchPartState.value

    val showToast = viewModel.showToast.value
    val context = LocalContext.current

    val historyList = viewModel.historyList.value
    val historyIsLoading = viewModel.historyIsLoading.value

    val optionsHistoryDialogIsShown = viewModel.showHistoryOptionsDialog.value
    val confirmDeleteDialogIsShown = viewModel.showConfirmDeleteDialog.value

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val showButtonForScroll by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 13 }
    }

    val orderIsNew = viewModel.currentOrderHistoryIsNew.value

    LaunchedEffect(showToast) {
        if (showToast){
            Toast.makeText(context, "Поле не должно быть пустым",
                Toast.LENGTH_SHORT).show()
            viewModel.resetShowToast()
        }
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
                text = "Поиск",
                fontSize = 28.sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )

            IconButton(
                onClick = {
                    navController.navigate(Screen.FavouritesScreen)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_favourite),
                    contentDescription = "Перейти к избранным",
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
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .height(130.dp)//фиксированый размер для верного отображения загрузки
                    ){
                        if (!searchPartState.isLoading){
                            SearchPart(
                                textInSearchField = searchPartState.textInTextField,
                                onTextChange = { newText ->
                                    viewModel.changeSearchText(newText)
                                },
                                onSearchClick = { viewModel.getTranslation() },
                                requestText = searchPartState.requestText,
                                resultText = searchPartState.resultText,
                                onEraseClick = { viewModel.onEraseSearchTextClick() }
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ){
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                item {
                    if (historyList.isNotEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = "История поиска",
                                    fontSize = 30.sp,
                                    modifier = Modifier
                                )

                                IconButton(
                                    onClick = { viewModel.historyOptionsDialogShow(true) },
                                    modifier = Modifier
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Настройки истории",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(bottom = 10.dp)
                                    )
                                }
                            }
                        }
                    } else if (!historyIsLoading) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text(
                                text = "История поиска пуста",
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                item {
                    if (historyIsLoading){
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ){
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                itemsIndexed(historyList) { position, word ->
                    Column(
                        Modifier.padding(horizontal = 8.dp)
                    ) {
                        HorizontalDivider(thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .align(Alignment.CenterHorizontally))

                        ItemHistory(
                            position = position + 1,
                            english = word.english,
                            russian = word.russian,
                            isFavouriteWord = viewModel.isFavouriteWord(word),
                            onDeleteClick = {
                                viewModel.changeCurrentWordForDialog(word)
                                viewModel.confirmDeleteDialogShow(true)
                            },
                            onFavouriteClick = {
                                viewModel.onFavouriteIconClick(word)
                            }
                        )

                        //для создания HorizontalDivider после последнего элемента
                        if (position + 1 == historyList.size){
                            HorizontalDivider(thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                        //чтобы место осталось для кнопки прокрута наверх, поскольку контент закрывать нельзя
                        if (position + 1 == historyList.size && showButtonForScroll){
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

    if (optionsHistoryDialogIsShown){
        OptionsHistoryDialog(
            orderNew = orderIsNew,
            onChangeOrderClick = {
                viewModel.changeOrderHistory(it)
            },
            onClearHistoryClick = {
                viewModel.clearHistory()
            },
            onHideDialogClick = {
                viewModel.historyOptionsDialogShow(false)
            }
        )
    }

    if (confirmDeleteDialogIsShown){
        ConfirmDeleteDialog(
            text = "Вы уверены, что хотите удалить слово из истории?",
            onConfirmClick = {
                viewModel.deleteWordFromHistory()
                viewModel.confirmDeleteDialogShow(false)
            },
            onCancelClick = {
                viewModel.confirmDeleteDialogShow(false)
            }
        )
    }
}