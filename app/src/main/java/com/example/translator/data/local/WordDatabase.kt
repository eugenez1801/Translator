package com.example.translator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.translator.domain.model.local.FavouriteWordEntity
import com.example.translator.domain.model.local.WordEntity

@Database(entities = [WordEntity::class, FavouriteWordEntity::class], version = 1)
abstract class WordDatabase: RoomDatabase() {
    abstract fun dao(): WordDao
}