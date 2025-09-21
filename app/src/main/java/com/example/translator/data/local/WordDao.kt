package com.example.translator.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.translator.domain.model.local.WordEntity

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWordToHistory(word: WordEntity)

    @Query("SELECT * FROM WordEntity ORDER BY id DESC")
    suspend fun getHistory(): List<WordEntity>

    @Query("DELETE FROM WordEntity")
    suspend fun clearHistory()

    @Delete
    suspend fun deleteWordFromHistory(word: WordEntity)

    @Query("UPDATE WordEntity SET isFavourite = :isFavourite WHERE english = :english")
    suspend fun changeWordIsFavourite(english: String, isFavourite: Boolean)

    @Query("SELECT * FROM WordEntity WHERE isFavourite = 1")//1 == true
    suspend fun getFavouriteWords(): List<WordEntity>
}