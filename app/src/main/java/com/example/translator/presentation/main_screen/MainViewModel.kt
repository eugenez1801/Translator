package com.example.translator.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.common.Resource
import com.example.translator.domain.use_case.GetTranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTranslationUseCase: GetTranslationUseCase
): ViewModel(){
    private val _textSearchField = mutableStateOf("")
    val textSearchField: State<String> = _textSearchField
    fun changeSearchText(newText: String){
        _textSearchField.value = newText
    }

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _resultText = mutableStateOf("")
    val resultText: State<String> = _resultText

    fun getTranslation(){
        getTranslationUseCase(_textSearchField.value).onEach { result ->
            when(result){
                is Resource.Success ->{
                    _isLoading.value = false
                    _resultText.value = result.data
                }
                is Resource.Loading -> {
                    _isLoading.value = true
                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _resultText.value = result.error
                }
            }
        }.launchIn(viewModelScope)
    }
}