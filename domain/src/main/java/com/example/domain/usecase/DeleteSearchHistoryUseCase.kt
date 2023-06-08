package com.example.domain.usecase

import com.example.domain.repository.SearchHistoryRepository

class DeleteSearchHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository) {
    suspend operator fun invoke(query: String)  = searchHistoryRepository.deleteSearchHistory(query)
}