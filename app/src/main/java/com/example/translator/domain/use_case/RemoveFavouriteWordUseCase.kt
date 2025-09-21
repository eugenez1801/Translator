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

        if (favouriteWordEntity != null){//для случая, когда удаление происходит с экрана избранных слов
            repository.removeFavouriteWord(favouriteWordEntity)
        }

        else if (wordEntity != null){//для случая, когда удаление избранного слова происходит из истории
            val favouriteWord = repository.getFavouriteWordByEnglish(wordEntity.english)
            repository.removeFavouriteWord(favouriteWord)
        }
    }
}