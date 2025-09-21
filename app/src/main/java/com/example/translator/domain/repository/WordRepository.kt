package com.example.translator.domain.repository

import com.example.translator.domain.model.local.WordEntity

interface WordRepository {
    suspend fun addWordToHistory(word: WordEntity)

    suspend fun getHistory(): List<WordEntity>

    suspend fun clearHistory()

    suspend fun deleteWordFromHistory(word: WordEntity)

    suspend fun changeWordIsFavourite(english: String, isFavourite: Boolean)

    suspend fun getFavouriteWords(): List<WordEntity>
}