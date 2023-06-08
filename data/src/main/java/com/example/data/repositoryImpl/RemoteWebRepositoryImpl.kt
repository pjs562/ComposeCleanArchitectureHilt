package com.example.data.repositoryImpl

import com.example.data.api.WebApi
import com.example.domain.repository.WebRepository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.mapper.WebMapper
import com.example.data.paging.WebPagingSource

class RemoteWebRepositoryImpl constructor(
    private val webApi: WebApi,
    private val webMapper: WebMapper
) : WebRepository{
    override fun getWebPagingSource(query: String, sort: String, limit: Int) = Pager(
        config = PagingConfig(limit)
    ){
        WebPagingSource(webApi, webMapper, query, sort, limit)
    }.flow
}