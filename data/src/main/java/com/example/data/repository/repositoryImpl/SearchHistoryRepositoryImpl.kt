package com.example.data.repository.repositoryImpl

import com.example.data.mapper.SearchHistoryMapper
import com.example.data.repository.dataSource.LocalSearchHistoryDataSource
import com.example.domain.model.SearchHistory
import com.example.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryRepositoryImpl(
    private val localSearchHistoryDataSource: LocalSearchHistoryDataSource,
    private val searchHistoryMapper: SearchHistoryMapper
): SearchHistoryRepository {
    override fun getSearchHistories(): Flow<List<SearchHistory>> = localSearchHistoryDataSource.getSearchHistories().map { 
        searchHistoryEntityList -> 
        searchHistoryEntityList.map { 
            searchHistoryEntity ->
            searchHistoryMapper.entityToDomain(searchHistoryEntity)
        }
    }
    

    override suspend fun insertSearchHistory(item: SearchHistory) = localSearchHistoryDataSource.insert(searchHistoryMapper.domainToEntity(item))

    override suspend fun deleteSearchHistory(query: String) = localSearchHistoryDataSource.delete(query)
}