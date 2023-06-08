package com.example.composecleanarchitecturehilt.di

import com.example.data.db.SearchHistoryDao
import com.example.data.repository.dataSource.LocalSearchHistoryDataSource
import com.example.data.repository.dataSourceImpl.LocalSearchHistoryDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    fun provideLocalDataSource(searchHistoryDao: SearchHistoryDao): LocalSearchHistoryDataSource = LocalSearchHistoryDataSourceImpl(searchHistoryDao = searchHistoryDao)
}