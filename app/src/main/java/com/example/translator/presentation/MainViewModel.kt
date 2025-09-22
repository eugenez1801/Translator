package com.example.translator.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.common.Resource
import com.example.translator.domain.model.local.FavouriteWordEntity
import com.example.translator.domain.model.local.WordEntity
import com.example.translator.domain.use_case.ClearHistoryUseCase
import com.example.translator.domain.use_case.DeleteWordFromHistoryUseCase
import com.example.translator.domain.use_case.GetFavouriteWordsUseCase
import com.example.translator.domain.use_case.GetHistoryUseCase
import com.example.translator.domain.use_case.GetTranslationUseCase
import com.example.translator.domain.use_case.MakeWordFavouriteUseCase
import com.example.translator.domain.use_case.RemoveFavouriteWordUseCase
import com.example.translator.presentation.main_screen.components.SearchPartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTranslationUseCase: GetTranslationUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteWordFromHistoryUseCase: DeleteWordFromHistoryUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase,
    private val getFavouriteWordsUseCase: GetFavouriteWordsUseCase,
    private val makeWordFavouriteUseCase: MakeWordFavouriteUseCase,
    private val removeFavouriteWordUseCase: RemoveFavouriteWordUseCase
): ViewModel(){

    //работа с текстовым полем поиска в SearchPart
    private val _searchPartState = mutableStateOf(SearchPartState())
    val searchPartState: State<SearchPartState> = _searchPartState
    fun changeSearchText(newText: String){
        //небольшая валидация
        if (newText.length == 0){
            _searchPartState.value = _searchPartState.value.copy(
                textInTextField = ""
            )
        }
        else if (!newText.endsWith("  ") && newText.isNotBlank()){
            _searchPartState.value = _searchPartState.value.copy(
                textInTextField = newText
            )
        }
    }
    //для кнопки очистки текстового поля поиска
    fun onEraseSearchTextClick(){
        _searchPartState.value = _searchPartState.value.copy(
            textInTextField = ""
        )
    }

    //toast для сообщения о пустой строке при поиске
    private val _showToast = mutableStateOf(false)
    val showToast: State<Boolean> = _showToast
    fun resetShowToast(){
        _showToast.value = false
    }

    //хранит список слов для истории
    private val _historyList = mutableStateOf(emptyList<WordEntity>())
    val historyList: State<List<WordEntity>> = _historyList

    //нужна для того, чтобы при запуске приложения была загрузка истории вместо пустого экрана
    private val _historyIsLoading = mutableStateOf(true)
    val historyIsLoading: State<Boolean> = _historyIsLoading

    //показывается ли диалог для настройки истории
    private val _showHistoryOptionsDialog = mutableStateOf(false)
    val showHistoryOptionsDialog: State<Boolean> = _showHistoryOptionsDialog
    fun historyOptionsDialogShow(isShown: Boolean){
        _showHistoryOptionsDialog.value = isShown
    }

    //для работы с удалением слова из истории поиска
    private val _showConfirmDeleteDialog = mutableStateOf(false)
    val showConfirmDeleteDialog: State<Boolean> = _showConfirmDeleteDialog
    fun confirmDeleteDialogShow(isShown: Boolean){
        _showConfirmDeleteDialog.value = isShown
    }
    //хранит текущее выбранное слово из истории
    private val _wordForConfirmDeleteDialog = mutableStateOf<WordEntity?>(null)
    fun changeCurrentWordForDialog(word: WordEntity){
        _wordForConfirmDeleteDialog.value = word
    }

    //эта переменная для экрана с историей поиска
    //храним english под String для приведения wordEntity и favouriteWordEntity к общему виду
    private val _setOfFavouriteWords = mutableStateSetOf<String>()

    //эта переменная для экрана с избранными
    private val _favouriteWordsList = mutableStateOf(emptyList<FavouriteWordEntity>())
    val favouriteWordsList: State<List<FavouriteWordEntity>> = _favouriteWordsList

    //для работы с удалением слова из списка избранных
    private val _showConfirmFavouriteDeleteDialog = mutableStateOf(false)
    val showConfirmFavouriteDeleteDialog: State<Boolean> = _showConfirmFavouriteDeleteDialog
    fun confirmFavouriteDeleteDialogShow(isShown: Boolean){
        _showConfirmFavouriteDeleteDialog.value = isShown
    }
    //хранит текущее выбранное избранное слово
    private val _wordForConfirmFavouriteDeleteDialog = mutableStateOf<FavouriteWordEntity?>(null)
    fun changeCurrentFavouriteWordForDialog(word: FavouriteWordEntity){
        _wordForConfirmFavouriteDeleteDialog.value = word
    }

    //текущая сортировка истории == Сначала новые?
    private val _currentOrderHistoryIsNew = mutableStateOf(true)
    val currentOrderHistoryIsNew: State<Boolean> = _currentOrderHistoryIsNew
    fun changeOrderHistory(isNew: Boolean){
        if (_currentOrderHistoryIsNew.value != isNew){
            _historyList.value = _historyList.value.reversed()
        }
        _currentOrderHistoryIsNew.value = isNew
    }

    //текущая сортировка избранных слов == Сначала новые?
    private val _currentOrderFavouriteIsNew = mutableStateOf(true)
    val currentOrderFavouriteIsNew: State<Boolean> = _currentOrderFavouriteIsNew
    fun changeOrderFavourite(isNew: Boolean){
        if (_currentOrderFavouriteIsNew.value != isNew){
            _favouriteWordsList.value = _favouriteWordsList.value.reversed()
        }
        _currentOrderFavouriteIsNew.value = isNew
    }

    //показывается ли на экране секция с настройками списка избранных слов
    private val _isOptionSectionVisible = mutableStateOf(false)
    val isOptionSectionVisible: State<Boolean> = _isOptionSectionVisible
    fun onOptionSectionClick(){
        _isOptionSectionVisible.value = !_isOptionSectionVisible.value
    }

    init {
        //с самого начала получаем историю и список избранных слов
        viewModelScope.launch {
            updateHistory()
            getFavouriteWords()
        }
    }

    fun getTranslation(){
        //если поле для ввода пустое, то ничего не выполняется
        if (_searchPartState.value.textInTextField.isBlank()){
            _showToast.value = true
            return
        }

        _searchPartState.value = _searchPartState.value.copy(
            requestText = "Вы ввели: ${_searchPartState.value.textInTextField}"
        )

        getTranslationUseCase(_searchPartState.value.textInTextField).onEach { result ->
            when(result){
                is Resource.Success ->{
                    _searchPartState.value = _searchPartState.value.copy(
                        isLoading = false,
                        resultText = "Перевод: ${result.data}"
                    )
                    updateHistory()
                }
                is Resource.Loading -> {
                    _searchPartState.value = _searchPartState.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _searchPartState.value = _searchPartState.value.copy(
                        isLoading = false,
                        resultText = "Ошибка: ${result.error}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    //получение актуальной истории
    private suspend fun updateHistory(){
        //зависит от текущего состояния сортировки (если она со старых начинается, то переворачиваем список)
        if (_currentOrderHistoryIsNew.value){
            _historyList.value = getHistoryUseCase()
        }
        else{
            _historyList.value = getHistoryUseCase().reversed()
        }
        _historyIsLoading.value = false
    }

    //для удаления из истории (в диалоговом окне)
    fun deleteWordFromHistory(){
        viewModelScope.launch {
            deleteWordFromHistoryUseCase(_wordForConfirmDeleteDialog.value!!)
            updateHistory()
        }
    }

    fun clearHistory(){
        viewModelScope.launch {
            clearHistoryUseCase()
            updateHistory()
        }
    }

    //нужен для экрана с историей запросов
    fun onFavouriteIconClick(word: WordEntity){
        if (!_setOfFavouriteWords.contains(word.english)){
            makeWordFavourite(word)
        } else{
            removeFavouriteWord(word)
        }
    }

    //нужен для добавления избранного слова
    private fun makeWordFavourite(word: WordEntity){
        _setOfFavouriteWords.add(word.english)
        viewModelScope.launch {
            makeWordFavouriteUseCase(word)
            getFavouriteWords()
        }
    }

    //нужен для удаления избранного слова
    private fun removeFavouriteWord(word: WordEntity){
        _setOfFavouriteWords.remove(word.english)
        viewModelScope.launch {
            removeFavouriteWordUseCase(wordEntity = word)
            getFavouriteWords()
        }
    }

    //нужен для экрана со списком избранных (в диалоговом окне)
    fun deleteFavouriteWordFromList(){
        _setOfFavouriteWords.remove(_wordForConfirmFavouriteDeleteDialog.value!!.english)
        viewModelScope.launch {
            removeFavouriteWordUseCase(favouriteWordEntity = _wordForConfirmFavouriteDeleteDialog.value!!)
            getFavouriteWords()
        }
    }

    //получение актуального списка избранных слов
    private suspend fun getFavouriteWords(){
        val favouriteWords = getFavouriteWordsUseCase()
        _setOfFavouriteWords.addAll(favouriteWords.map { it.english })
        if (_currentOrderFavouriteIsNew.value) _favouriteWordsList.value = favouriteWords
        else _favouriteWordsList.value = favouriteWords.reversed()
    }

    //определение является ли слово из истории избранным
    fun isFavouriteWord(word: WordEntity): Boolean{
        return _setOfFavouriteWords.contains(word.english)
    }
}