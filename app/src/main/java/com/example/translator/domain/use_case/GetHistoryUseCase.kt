package com.example.translator.domain.use_case

import com.example.translator.domain.model.local.WordEntity
import com.example.translator.domain.repository.WordRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: WordRepository
){
    suspend operator fun invoke(): List<WordEntity>{
        val startList = repository.getHistory()
        return startList.map { it.formatForUi() }
    }
}

fun WordEntity.formatForUi(): WordEntity{
    return this.copy(
        russian = this.russian.replaceFirstChar { it.uppercaseChar() },
        english = this.english.replaceFirstChar { it.uppercaseChar() }
    )
}