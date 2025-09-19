package com.example.translator.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.translator.domain.model.local.WordEntity
import java.util.UUID

@Dao
interface WordDao {
    @Insert
    suspend fun addWordToHistory(word: WordEntity)

    @Query("SELECT * FROM WordEntity")
    suspend fun getHistory(): List<WordEntity>

    @Query("SELECT * FROM WordEntity WHERE id = :id")
    suspend fun getWordById(id: UUID): WordEntity

    @Delete
    suspend fun deleteWordFromHistory(word: WordEntity)

    @Query("UPDATE WordEntity SET isFavourite = :isFavourite WHERE id = :id")
    suspend fun changeWordIsFavourite(id: UUID, isFavourite: Boolean)

    @Query("SELECT * FROM WordEntity WHERE isFavourite = 1")//1 == true
    suspend fun getFavouriteWords(): List<WordEntity>
}