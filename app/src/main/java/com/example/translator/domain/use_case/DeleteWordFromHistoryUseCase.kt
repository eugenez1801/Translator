package com.example.translator.domain.use_case

import com.example.translator.domain.model.local.WordEntity
import com.example.translator.domain.repository.WordRepository
import javax.inject.Inject

class DeleteWordFromHistoryUseCase @Inject constructor(
    private val repository: WordRepository
) {
    suspend operator fun invoke(word: WordEntity){
        repository.deleteWordFromHistory(word.formatForDatabase())
    }
}

fun WordEntity.formatForDatabase(): WordEntity{
    return this.copy(
        russian = this.russian.replaceFirstChar { it.lowercaseChar() },
        english = this.english.replaceFirstChar { it.lowercaseChar() }
    )
}