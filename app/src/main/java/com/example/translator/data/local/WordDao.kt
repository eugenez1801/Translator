package com.example.translator.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.translator.domain.model.local.FavouriteWordEntity
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



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun makeWordFavourite(favouriteWord: FavouriteWordEntity)

    @Delete
    suspend fun removeFavouriteWord(favouriteWord: FavouriteWordEntity)

    @Query("SELECT * FROM FavouriteWordEntity WHERE english = :english")
    suspend fun getFavouriteWordByEnglish(english: String): FavouriteWordEntity

    @Query("SELECT * FROM FavouriteWordEntity ORDER BY id DESC")
    suspend fun getFavouriteWords(): List<FavouriteWordEntity>
}