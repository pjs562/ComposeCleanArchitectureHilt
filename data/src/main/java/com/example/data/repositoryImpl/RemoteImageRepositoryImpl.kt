package com.example.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.api.ImageApi
import com.example.data.mapper.ImageMapper
import com.example.data.paging.ImagePagingSource
import com.example.domain.repository.ImageRepository

class RemoteImageRepositoryImpl (
    private val imageApi: ImageApi,
    private val imageMapper: ImageMapper,
) : ImageRepository {
    override fun getImagePagingSource(
        query: String,
        sort: String,
        limit: Int,
    ) = Pager(
        config = PagingConfig(limit)
    ){
        ImagePagingSource(imageApi, imageMapper, query, sort, limit)
    }.flow
}