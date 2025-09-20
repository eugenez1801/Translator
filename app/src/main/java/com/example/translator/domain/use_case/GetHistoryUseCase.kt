package com.example.translator.domain.use_case

import com.example.translator.domain.model.local.WordEntity
import com.example.translator.domain.repository.WordRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: WordRepository
){
    suspend operator fun invoke(): List<WordEntity>{
        return repository.getHistory()
    }
}