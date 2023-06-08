package com.example.domain.usecase

import com.example.domain.model.SearchHistory
import com.example.domain.repository.SearchHistoryRepository

class SearchHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository) {
    operator fun invoke() = searchHistoryRepository.getSearchHistories()

    suspend fun insertSearchHistory(item: SearchHistory) =
        searchHistoryRepository.insertSearchHistory(item)

    suspend fun deleteSearchHistory(query: String) = searchHistoryRepository.deleteSearchHistory(query)
}