package com.example.translator.domain.model.remote

data class Meanings(
    var id: Int,
    var partOfSpeechCode: String,
    var translation: Translation,
    var previewUrl: String,
    var imageUrl: String,
    var transcription: String,
    var soundUrl: String,
)