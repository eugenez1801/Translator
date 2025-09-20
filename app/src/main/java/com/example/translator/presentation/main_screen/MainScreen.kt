package com.example.translator.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.presentation.main_screen.components.ItemHistory
import com.example.translator.presentation.main_screen.components.SearchPart

@Composable
fun MainScreen(
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

    LaunchedEffect(showToast) {
        if (showToast){
            Toast.makeText(context, "Поле не должно быть пустым",
                Toast.LENGTH_SHORT).show()
            viewModel.resetShowToast()
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
                        .height(190.dp)//чтобы фиксированый размер был для верного отображения загрузки
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
                        Text(
                            text = "История поиска",
                            fontSize = 30.sp,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
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

            items(historyList) { word ->
                Column(
                    Modifier.padding(horizontal = 20.dp)
                ) {
                    HorizontalDivider(thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .align(Alignment.CenterHorizontally))

                    ItemHistory(
                        english = word.english,
                        russian = word.russian,
                        onDeleteClick = {
                            viewModel.deleteWordFromHistory(word)
                        }
                    )
                }
            }
        }
    }
}