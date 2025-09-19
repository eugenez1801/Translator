package com.example.translator.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.example.translator.domain.use_case.GetTranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTranslationUseCase: GetTranslationUseCase
): ViewModel(){

}