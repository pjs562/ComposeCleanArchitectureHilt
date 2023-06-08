package com.example.data.remote.mapper

import com.example.data.remote.response.ImageInfo
import com.example.data.remote.response.ImageResponse
import com.example.domain.model.ImageEntity

class ImageMapper {
    fun fromResponse(
        response: ImageResponse
    ): List<ImageEntity> = response.documents.map {
        fromReponse(it)
    }

    private fun fromReponse(
        response: ImageInfo
    ) : ImageEntity = ImageEntity(
        response.thumbnailUrl,
        response.displaySitename,
        response.docUrl
    )
}