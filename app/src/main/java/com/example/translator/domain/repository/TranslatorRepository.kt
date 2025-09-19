package com.example.translator.domain.repository

interface TranslatorRepository {
    fun getTranslation(word: String): String
}