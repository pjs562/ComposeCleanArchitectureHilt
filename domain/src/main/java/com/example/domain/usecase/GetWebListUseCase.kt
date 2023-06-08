package com.example.domain.usecase

import com.example.domain.repository.WebRepository

class GetWebListUseCase(
    private val webRepository: WebRepository,
){
    operator fun invoke(
        query: String,
        sort: String = "accuracy",
        limit: Int = 20
    ) = webRepository.getWebPagingSource(query, sort, limit)
}