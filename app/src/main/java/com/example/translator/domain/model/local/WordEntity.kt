package com.example.translator.domain.model.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["english"], unique = true)])
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val russian: String,
    val english: String,
    var isFavourite: Boolean
)
