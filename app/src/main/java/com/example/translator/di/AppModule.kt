package com.example.translator.di

import com.example.translator.data.remote.TranslatorApi
import com.example.translator.data.repository.TranslatorRepositoryImpl
import com.example.translator.domain.repository.TranslatorRepository
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
}