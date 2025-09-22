package com.example.translator.domain.use_case

import com.example.translator.domain.model.local.FavouriteWordEntity
import com.example.translator.domain.model.local.WordEntity
import com.example.translator.domain.repository.WordRepository
import javax.inject.Inject

class RemoveFavouriteWordUseCase @Inject constructor(
    private val repository: WordRepository
) {
    suspend operator fun invoke(favouriteWordEntity: FavouriteWordEntity? = null,
                                wordEntity: WordEntity? = null){

        //для случая, когда удаление избранного слова происходит с экрана избранных слов
        if (favouriteWordEntity != null){
            repository.removeFavouriteWord(favouriteWordEntity)
        }

        //для случая, когда удаление избранного слова происходит из истории
        else if (wordEntity != null){
            val favouriteWord = repository.getFavouriteWordByEnglish(wordEntity.english)
            repository.removeFavouriteWord(favouriteWord)
        }
    }
}