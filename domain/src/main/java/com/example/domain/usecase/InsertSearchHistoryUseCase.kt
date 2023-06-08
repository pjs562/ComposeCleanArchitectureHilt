package com.example.domain.usecase

import com.example.domain.model.SearchHistory
import com.example.domain.repository.SearchHistoryRepository

class InsertSearchHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository) {
    suspend operator fun invoke(item: SearchHistory) = searchHistoryRepository.insertSearchHistory(item)
}