package com.base.model.local

import com.base.model.retrofit.response.songListResponse.SongListResponse

class SongListWraper(var songListResponse: SongListResponse, var isFavorite: Boolean? = false,
                          var isPlaying: Boolean? = false, var lastPlayMillis: Int? = 0) {

    fun changeFavoriteState() {
        isFavorite = isFavorite != true
    }
}