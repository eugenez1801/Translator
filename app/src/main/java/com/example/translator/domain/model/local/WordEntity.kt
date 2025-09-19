package com.example.translator.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class WordEntity(
    @PrimaryKey val id: UUID,
    val russian: String,
    val english: String,
    var isFavourite: Boolean
)
