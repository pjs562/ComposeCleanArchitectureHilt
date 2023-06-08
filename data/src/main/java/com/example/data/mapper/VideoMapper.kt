package com.example.data.mapper

import com.example.data.response.VideoInfo
import com.example.data.response.VideoResponse
import com.example.domain.model.VideoEntity

class VideoMapper {
    fun fromResponse(
        response: VideoResponse
    ): List<VideoEntity> = response.documents.map {
        fromReponse(it)
    }

    private fun fromReponse(
        response: VideoInfo
    ) : VideoEntity = VideoEntity(
        response.title,
        response.playTime,
        response.thumbnail,
        response.url,
        response.datetime,
        response.author
    )
}