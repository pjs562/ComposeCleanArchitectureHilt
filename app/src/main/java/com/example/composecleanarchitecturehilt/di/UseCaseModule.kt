package com.example.composecleanarchitecturehilt.di

import com.example.domain.repository.ImageRepository
import com.example.domain.repository.SearchHistoryRepository
import com.example.domain.repository.VideoRepository
import com.example.domain.repository.WebRepository
import com.example.domain.usecase.DeleteSearchHistoryUseCase
import com.example.domain.usecase.GetImageListUseCase
import com.example.domain.usecase.GetSearchHistoryListUseCase
import com.example.domain.usecase.GetVideoListUseCase
import com.example.domain.usecase.GetWebListUseCase
import com.example.domain.usecase.InsertSearchHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetWebListUseCase(
        webRepository: WebRepository
    ): GetWebListUseCase = GetWebListUseCase(webRepository)

    @Provides
    @Singleton
    fun provideGetVideoListUseCase(
        videoRepository: VideoRepository
    ): GetVideoListUseCase = GetVideoListUseCase(videoRepository)

    @Provides
    @Singleton
    fun provideGetImageListUseCase(
        imageRepository: ImageRepository
    ): GetImageListUseCase = GetImageListUseCase(imageRepository)

    @Provides
    @Singleton
    fun provideGetHistoryUseCase(
        searchHistoryRepository: SearchHistoryRepository
    ): GetSearchHistoryListUseCase = GetSearchHistoryListUseCase(searchHistoryRepository)

    @Provides
    @Singleton
    fun provideInsertSearchHistoryUseCase(
        searchHistoryRepository: SearchHistoryRepository
    ): InsertSearchHistoryUseCase = InsertSearchHistoryUseCase(searchHistoryRepository)

    @Provides
    @Singleton
    fun provideDeleteSearchHistoryUseCase(
        searchHistoryRepository: SearchHistoryRepository
    ): DeleteSearchHistoryUseCase = DeleteSearchHistoryUseCase(searchHistoryRepository)
}