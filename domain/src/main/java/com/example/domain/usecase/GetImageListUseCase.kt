package com.example.domain.usecase

import com.example.domain.repository.ImageRepository

class GetImageListUseCase(
    private val imageRepository: ImageRepository,
){
    operator fun invoke(
        query: String,
        sort: String = "accuracy",
        limit: Int = 20
    ) = imageRepository.getImagePagingSource(query, sort, limit)
}