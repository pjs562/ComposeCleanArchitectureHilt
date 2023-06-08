package com.example.domain.repository

import com.example.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getSearchHistoryList(): Flow<List<SearchHistory>>

    suspend fun insertSearchHistory(item: SearchHistory)

    suspend fun deleteSearchHistory(query: String)
}