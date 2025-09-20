package com.example.translator.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntity(
    val russian: String,
    @PrimaryKey val english: String,
    var isFavourite: Boolean
)
