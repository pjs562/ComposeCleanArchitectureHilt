package com.example.data.mapper

import com.example.data.entities.SearchHistoryEntity
import com.example.domain.model.SearchHistory

class SearchHistoryMapper {
    fun entityToDomain(searchHistoryEntity: SearchHistoryEntity): SearchHistory {
        return SearchHistory(
            query = searchHistoryEntity.query,
            timestamp = searchHistoryEntity.timestamp,
        )
    }

    fun domainToEntity(searchHistory: SearchHistory): SearchHistoryEntity {
        return SearchHistoryEntity(
            query = searchHistory.query,
            timestamp = searchHistory.timestamp
        )
    }
}