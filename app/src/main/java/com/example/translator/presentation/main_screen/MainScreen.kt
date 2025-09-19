package com.example.translator.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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

    LaunchedEffect(showToast) {
        if (showToast == true){
            Toast.makeText(context, "Поле не должно быть пустым",
                Toast.LENGTH_SHORT).show()
            viewModel.resetShowToast()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()) {
        if (isLoading){
            Box(
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(0.2f)
            ){
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center))
            }
        }else{
            SearchPart(
                textInSearchField = textForSearchField,
                onTextChange = { newText ->
                    viewModel.changeSearchText(newText)
                },
                onSearchClick = {viewModel.getTranslation()},
                requestText = textForRequest,
                resultText = textForResult,
                onEraseClick = {viewModel.eraseSearchText()}
            )
        }
    }
}