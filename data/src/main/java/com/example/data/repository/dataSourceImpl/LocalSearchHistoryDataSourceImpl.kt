package com.example.data.repository.dataSourceImpl

import com.example.data.db.SearchHistoryDao
import com.example.data.entities.SearchHistoryEntity
import com.example.data.repository.dataSource.LocalSearchHistoryDataSource
import kotlinx.coroutines.flow.Flow

class LocalSearchHistoryDataSourceImpl(private val searchHistoryDao: SearchHistoryDao) : LocalSearchHistoryDataSource{
    override fun getSearchHistoryList(): Flow<List<SearchHistoryEntity>> = searchHistoryDao.getSearchHistoryList()

    override suspend fun insert(item: SearchHistoryEntity) = searchHistoryDao.insert(item)

    override suspend fun delete(query: String) = searchHistoryDao.delete(query)
}