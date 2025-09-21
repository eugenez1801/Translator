package com.example.translator.domain.use_case

import com.example.translator.domain.model.local.FavouriteWordEntity
import com.example.translator.domain.repository.WordRepository
import javax.inject.Inject

class GetFavouriteWordsUseCase @Inject constructor(
    private val repository: WordRepository
){
    suspend operator fun invoke(): List<FavouriteWordEntity> {
        return repository.getFavouriteWords()
    }
}