package com.example.translator.domain.model

data class Word(
    val id: Int,
    val text: String,
    val meanings: List<Meanings>
)