package com.example.translator.domain.use_case

import com.example.translator.domain.model.local.FavouriteWordEntity
import com.example.translator.domain.model.local.WordEntity
import com.example.translator.domain.repository.WordRepository
import javax.inject.Inject

class MakeWordFavouriteUseCase @Inject constructor(
    private val repository: WordRepository
) {
    suspend operator fun invoke(wordEntity: WordEntity){
        repository.makeWordFavourite(wordEntity.toFavouriteWordEntity())
    }
}

fun WordEntity.toFavouriteWordEntity(): FavouriteWordEntity{
    return FavouriteWordEntity(
        english = english,
        russian = russian
    )
}