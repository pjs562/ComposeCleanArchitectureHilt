package com.example.composecleanarchitecturehilt.di

import com.example.data.api.ImageApi
import com.example.data.mapper.SearchHistoryMapper
import com.example.data.api.VideoApi
import com.example.data.api.WebApi
import com.example.data.mapper.ImageMapper
import com.example.data.mapper.VideoMapper
import com.example.data.mapper.WebMapper
import com.example.data.repository.dataSource.LocalSearchHistoryDataSource
import com.example.data.repository.repositoryImpl.SearchHistoryRepositoryImpl
import com.example.data.repositoryImpl.RemoteImageRepositoryImpl
import com.example.data.repositoryImpl.RemoteVideoRepositoryImpl
import com.example.data.repositoryImpl.RemoteWebRepositoryImpl
import com.example.domain.repository.ImageRepository
import com.example.domain.repository.SearchHistoryRepository
import com.example.domain.repository.VideoRepository
import com.example.domain.repository.WebRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun remoteWebRepository(
        api: WebApi,
        mapper: WebMapper,
    ) : WebRepository = RemoteWebRepositoryImpl(api, mapper)

    @Singleton
    @Provides
    fun remoteVideoRepository(
        api: VideoApi,
        mapper: VideoMapper,
    ) : VideoRepository = RemoteVideoRepositoryImpl(api, mapper)

    @Singleton
    @Provides
    fun remoteImageRepository(
        api: ImageApi,
        mapper: ImageMapper,
    ) : ImageRepository = RemoteImageRepositoryImpl(api, mapper)
    @Provides
    fun provideLocalSearchHistoryRepository(
        localSearchHistoryDataSource: LocalSearchHistoryDataSource,
        searchHistoryMapper: SearchHistoryMapper
    ): SearchHistoryRepository = SearchHistoryRepositoryImpl(
        localSearchHistoryDataSource, searchHistoryMapper
    )
}