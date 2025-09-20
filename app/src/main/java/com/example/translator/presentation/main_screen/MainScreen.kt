package com.example.translator.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.R
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_uk),
                            contentDescription = "ukFlag",
                            tint = Color.Unspecified
                        )

                        Icon(
                            painter = painterResource(R.drawable.ic_russia),
                            contentDescription = "Russian flag",
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            items(historyList) { word ->
                ItemHistory(
                    word.english,
                    word.russian
                )
            }
        }
    }
}