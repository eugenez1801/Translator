package com.example.translator.domain.model.remote

import com.example.translator.domain.model.local.WordEntity

data class Word(
    val id: Int,
    val text: String,
    val meanings: List<Meanings>
)

fun Word.toWordEntity(): WordEntity{
    return WordEntity(
        russian = meanings[0].translation.text,
        english = text,
        isFavourite = false
    )
}