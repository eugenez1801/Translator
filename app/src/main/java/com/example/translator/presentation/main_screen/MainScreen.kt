package com.example.translator.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.translator.presentation.main_screen.dialogs.ConfirmDeleteDialog
import com.example.translator.presentation.main_screen.dialogs.OptionsHistoryDialog

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val textForSearchField = viewModel.textSearchField.value
    val textForRequest = viewModel.requestText.value
    val textForResult = viewModel.resultText.value

    val isLoading = viewModel.isLoading.value

    val showToast = viewModel.showToast.value
    val context = LocalContext.current

    val historyList = viewModel.historyList.value
    val historyIsLoading = viewModel.historyIsLoading.value

    val optionsHistoryDialogIsShown = viewModel.showHistoryOptionsDialog.value
    val confirmDeleteDialogIsShown = viewModel.showConfirmDeleteDialog.value

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
            .padding(15.dp)
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
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier
                            //уменьшили, поскольку верхний текст больше не является частью SearchPart
                            .height(123.dp)//чтобы фиксированый размер был для верного отображения загрузки
                    ){
                        if (!isLoading){
                            SearchPart(
                                textInSearchField = textForSearchField,
                                onTextChange = { newText ->
                                    viewModel.changeSearchText(newText)
                                },
                                onSearchClick = { viewModel.getTranslation() },
                                requestText = textForRequest,
                                resultText = textForResult,
                                onEraseClick = { viewModel.eraseSearchText() }
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ){
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
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
                                    onClick = { viewModel.showHistoryOptionsDialog(true) },
                                    modifier = Modifier
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Options of history",
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
                                    .align(Alignment.Center)
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
                            position = position,
                            english = word.english,
                            russian = word.russian,
                            isFavouriteWord = viewModel.isFavouriteWord(word),
                            onDeleteClick = {
                                viewModel.changeCurrentWordForDialog(word)
                                viewModel.showConfirmDeleteDialog(true)
                            },
                            onFavouriteClick = {
                                viewModel.onFavouriteIconClick(word)
                            }
                        )
                    }
                }
            }
        }
    }

    if (optionsHistoryDialogIsShown){
        OptionsHistoryDialog(
            onClearHistoryClick = {
                viewModel.clearHistory()
            },
            onHideDialogClick = {
                viewModel.showHistoryOptionsDialog(false)
            }
        )
    }

    if (confirmDeleteDialogIsShown){
        ConfirmDeleteDialog(
            onConfirmClick = {
                viewModel.deleteWordFromHistory()
                viewModel.showConfirmDeleteDialog(false)
            },
            onCancelClick = {
                viewModel.showConfirmDeleteDialog(false)
            }
        )
    }
}