package com.example.domain.usecase

import com.example.domain.repository.SearchHistoryRepository

class GetSearchHistoryListUseCase (private val searchHistoryRepository: SearchHistoryRepository){
    operator fun invoke() = searchHistoryRepository.getSearchHistoryList()
}