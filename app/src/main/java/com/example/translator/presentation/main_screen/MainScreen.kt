package com.example.translator.presentation.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.presentation.main_screen.components.SearchPart

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val textForSearchField = viewModel.textSearchField.value
    val textForResult = viewModel.resultText.value
    val isLoading = viewModel.isLoading.value

    Column(modifier = Modifier
        .fillMaxSize()) {
        if (isLoading){
            CircularProgressIndicator()
        }
        SearchPart(
            text = textForSearchField,
            onTextChange = { newText ->
                viewModel.changeSearchText(newText)
            },
            onSearchClick = {viewModel.getTranslation()},
            resultText = textForResult
        )
    }
}