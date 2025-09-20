package com.example.translator.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.common.Resource
import com.example.translator.domain.model.local.WordEntity
import com.example.translator.domain.use_case.GetHistoryUseCase
import com.example.translator.domain.use_case.GetTranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTranslationUseCase: GetTranslationUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
): ViewModel(){
    private val _textSearchField = mutableStateOf("")
    val textSearchField: State<String> = _textSearchField
    fun changeSearchText(newText: String){
        if (newText.length == 0) _textSearchField.value = ""//чтобы можно было очистить строку
        else if (!newText.endsWith("  ") && newText.isNotBlank())//немного валидации
            _textSearchField.value = newText
    }
    fun eraseSearchText(){
        _textSearchField.value = ""
    }

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _requestText = mutableStateOf("")//для отображения пользовательского запроса
    val requestText: State<String> = _requestText

    private val _resultText = mutableStateOf("")
    val resultText: State<String> = _resultText

    private val _showToast = mutableStateOf(false)
    val showToast: State<Boolean> = _showToast
    fun resetShowToast(){
        _showToast.value = false
    }

    private val _historyList = mutableStateOf(emptyList<WordEntity>())
    val historyList: State<List<WordEntity>> = _historyList

    private val _historyIsLoading = mutableStateOf(true)//для значка загрузки при запуске приложения
    val historyIsLoading: State<Boolean> = _historyIsLoading

    init {
        updateHistory()
    }

    fun getTranslation(){
        if (_textSearchField.value.isBlank()){
//            _resultText.value = "Поле не должно быть пустым" заменено на показ Toast
            _showToast.value = true
            return
        }

        _requestText.value = "Вы ввели: ${_textSearchField.value}"

        getTranslationUseCase(_textSearchField.value).onEach { result ->
            when(result){
                is Resource.Success ->{
                    _isLoading.value = false
                    _resultText.value = "Перевод: ${result.data}"
                    updateHistory()
                }
                is Resource.Loading -> {
                    _isLoading.value = true
                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _resultText.value = "Ошибка: ${result.error}"
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateHistory(){
        viewModelScope.launch {
            _historyList.value = getHistoryUseCase()
            _historyIsLoading.value = false
        }
    }
}