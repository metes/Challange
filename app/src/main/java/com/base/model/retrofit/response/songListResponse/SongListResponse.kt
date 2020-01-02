package com.base.model.retrofit.response.songListResponse

data class SongListResponse(
    val id: Int,
    val category: String,
    val fileAddress: String,
    val name: String
)