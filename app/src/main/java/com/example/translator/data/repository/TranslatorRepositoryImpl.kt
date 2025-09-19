package com.example.translator.data.repository

import com.example.translator.data.remote.TranslatorApi
import com.example.translator.domain.model.Word
import com.example.translator.domain.repository.TranslatorRepository
import javax.inject.Inject

class TranslatorRepositoryImpl @Inject constructor(
    val api: TranslatorApi
): TranslatorRepository {
    override suspend fun getTranslation(word: String): Word {
        return api.getTranslation(word)
    }
}