package com.example.translator.domain.model.remote

data class Word(
    val id: Int,
    val text: String,
    val meanings: List<Meanings>
)