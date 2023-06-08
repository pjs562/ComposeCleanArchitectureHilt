package com.example.data.repository.dataSource

import com.example.data.entities.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface LocalSearchHistoryDataSource {
    fun getSearchHistoryList(): Flow<List<SearchHistoryEntity>>

    suspend fun insert(item: SearchHistoryEntity)

    suspend fun delete(query: String)
}