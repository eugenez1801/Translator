package com.example.translator.domain.use_case

import com.example.translator.common.Resource
import com.example.translator.domain.model.local.WordEntity
import com.example.translator.domain.model.remote.toWordEntity
import com.example.translator.domain.repository.TranslatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class GetTranslationUseCase @Inject constructor(
    private val repository: TranslatorRepository,
    private val addWordToHistoryUseCase: AddWordToHistoryUseCase
) {
    operator fun invoke(word: String): Flow<Resource<String>> = flow{
        emit(Resource.Loading())
        try {
            val result = repository.getTranslation(word)
            if (result.isEmpty())
                throw ResultListException("не удалось перевести ваше слово")

            val wordEntity = result[0].toWordEntity().formatForDatabase()
            addWordToHistoryUseCase(wordEntity)//добавляем слово в историю
            //потом перевод отправляем для UI
            emit(Resource.Success(wordEntity.russian.replaceFirstChar { it.lowercaseChar() }))
        } catch (e: IOException){
            emit(Resource.Error("Не удалось получить ответ от сервера. Проверьте интернет соединение"))
        } catch (e: Exception){
            emit(Resource.Error(e.message ?: "Unexpected error"))
        }
    }
}

fun WordEntity.formatForDatabase(): WordEntity {
    return this.copy(
        russian = this.russian.replaceFirstChar { it.uppercaseChar() },
        english = this.english.replaceFirstChar { it.uppercaseChar() }
    )
}

class ResultListException(message: String): Exception(message)