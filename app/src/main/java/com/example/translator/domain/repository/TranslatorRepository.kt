package com.example.translator.domain.repository

import com.example.translator.domain.model.Word

interface TranslatorRepository {
    suspend fun getTranslation(word: String): List<Word>
}