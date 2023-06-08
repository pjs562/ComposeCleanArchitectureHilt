package com.example.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchHistory(
    @SerializedName("query")
    val query: String,
    @SerializedName("timestamp")
    val timestamp: Long,
) : Serializable
