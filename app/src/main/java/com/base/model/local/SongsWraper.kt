package com.base.model.local

import com.base.model.retrofit.response.songListResponse.SongListResponse

data class SongListWrapper(var songListResponse: SongListResponse, var isFavorite: Boolean? = false,
                      var isPlaying: Boolean? = false) {

    fun changeFavoriteState() {
        isFavorite = isFavorite != true
    }
}