package com.example.translator.domain.repository

import com.example.translator.domain.model.local.FavouriteWordEntity
import com.example.translator.domain.model.local.WordEntity

interface WordRepository {
    suspend fun addWordToHistory(word: WordEntity)

    suspend fun getHistory(): List<WordEntity>

    suspend fun clearHistory()

    suspend fun deleteWordFromHistory(word: WordEntity)

    suspend fun makeWordFavourite(favouriteWord: FavouriteWordEntity)

    suspend fun removeFavouriteWord(favouriteWord: FavouriteWordEntity)

    suspend fun getFavouriteWordByEnglish(english: String): FavouriteWordEntity

    suspend fun getFavouriteWords(): List<FavouriteWordEntity>
}