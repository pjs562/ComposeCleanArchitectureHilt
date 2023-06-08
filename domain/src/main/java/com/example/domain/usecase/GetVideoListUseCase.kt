package com.example.domain.usecase

import com.example.domain.repository.VideoRepository

class GetVideoListUseCase (
    private val videoRepository: VideoRepository,
){
    operator fun invoke(
        query: String,
        sort: String = "accuracy",
        limit: Int = 20
    ) = videoRepository.getVideoPagingSource(query, sort, limit)
}