package com.example.translator.data.repository

import com.example.translator.data.local.WordDao
import com.example.translator.domain.model.local.WordEntity
import com.example.translator.domain.repository.WordRepository

class WordRepositoryImpl(
    private val dao: WordDao
): WordRepository {
    override suspend fun addWordToHistory(word: WordEntity) {
        dao.addWordToHistory(word)
    }

    override suspend fun getHistory(): List<WordEntity> {
        return dao.getHistory()
    }

    override suspend fun clearHistory() {
        dao.clearHistory()
    }

    override suspend fun deleteWordFromHistory(word: WordEntity) {
        dao.deleteWordFromHistory(word)
    }

    override suspend fun changeWordIsFavourite(english: String, isFavourite: Boolean) {
        dao.changeWordIsFavourite(english, isFavourite)
    }

    override suspend fun getFavouriteWords(): List<WordEntity> {
        return dao.getFavouriteWords()
    }
}