package com.example.translator.domain.use_case

import com.example.translator.common.Resource
import com.example.translator.domain.repository.TranslatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTranslationUseCase @Inject constructor(
    private val repository: TranslatorRepository
) {
    operator fun invoke(word: String): Flow<Resource<String>> = flow{
        emit(Resource.Loading())
        try {
            val result = repository.getTranslation(word)
            val translationWord = result[0].meanings[0].translation.text//не уверен пока, что это лучше, чем передавать сразу result
            emit(Resource.Success(translationWord))
        } catch (e: Exception){
            emit(Resource.Error(e.message ?: "Unexpected error"))
        }
    }
}