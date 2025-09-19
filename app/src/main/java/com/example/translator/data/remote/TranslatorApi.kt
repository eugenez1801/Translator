package com.example.translator.data.remote

import com.example.translator.domain.model.Word
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslatorApi {
    @GET("api/public/v1/words/search")
    fun getTranslation(@Query("search") word: String): Word
}