package com.example.data.mapper

import com.example.data.extensions.decode
import com.example.data.response.WebInfo
import com.example.data.response.WebResponse
import com.example.domain.model.WebEntity

class WebMapper {
    fun fromResponse(
        response: WebResponse
    ): List<WebEntity> = response.documents.map {
        fromReponse(it)
    }

    private fun fromReponse(
        response: WebInfo
    ) : WebEntity = WebEntity(
        response.title.decode(),
        response.contents.decode(),
        response.url,
        response.datetime
    )
}