package com.example.translator.presentation.main_screen.components

data class SearchPartState(
    val textInTextField: String = "",
    val requestText: String = "",
    val resultText: String = "",
    val isLoading: Boolean = false
)