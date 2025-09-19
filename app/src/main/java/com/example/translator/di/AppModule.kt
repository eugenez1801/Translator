package com.example.translator.di

import android.app.Application
import androidx.room.Room
import com.example.translator.data.local.WordDatabase
import com.example.translator.data.remote.TranslatorApi
import com.example.translator.data.repository.TranslatorRepositoryImpl
import com.example.translator.data.repository.WordRepositoryImpl
import com.example.translator.domain.repository.TranslatorRepository
import com.example.translator.domain.repository.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTranslatorApi(): TranslatorApi {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitBuilder.create(TranslatorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTranslatorRepository(api: TranslatorApi): TranslatorRepository{
        return TranslatorRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideWordDatabase(app: Application): WordDatabase{
        return Room.databaseBuilder(
            app,
            WordDatabase::class.java,
            "word_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWordRepository(db: WordDatabase): WordRepository{
        return WordRepositoryImpl(db.dao())
    }
}