package com.base.repository.network

import com.base.model.retrofit.response.GenericResponse
import com.base.model.retrofit.response.songListResponse.SongListResponse
import retrofit2.http.GET

interface APIInterface {

    @GET("/bins/1dcbc0")
    suspend fun getSongList(): GenericResponse<List<SongListResponse>>


}
