package com.base.ui.fragment.songs.parent

import android.app.Application
import android.util.SparseArray
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.base.base.BaseViewModel
import com.base.commons.PlayerHelper
import com.base.commons.SharedPrefHelper
import com.base.model.local.SongListWrapper
import com.base.model.retrofit.response.songListResponse.SongListResponse
import com.base.network.APIClient
import org.koin.core.KoinComponent
import org.koin.core.inject

class SongsVM(private val app: Application, private val client: APIClient) :
    BaseViewModel(app, client), KoinComponent {

    // Her bir ses dosyasi icin map tutulıyor
    var mediaPlayerMap = SparseArray<PlayerHelper>()

    var allSongsLD: LiveData<ArrayList<SongListWrapper>> = MutableLiveData()
    var favoritesSongsLD: LiveData<ArrayList<SongListWrapper>> = MutableLiveData()

    // Sonraki oturumlarda hatirlanmasi icin degisiklikler disk'e kaydediliyor
    private val sharedPrefHelper: SharedPrefHelper by inject()

    init {
        getAllSongs()
    }

    private fun getAllSongs() {
        sendRequest(
            { client.songListClient() },
            {
                generateAllSongLD(it)
            }
        )
    }

    private fun generateAllSongLD(songList: List<SongListResponse>?) {
        val savedFavoriteSongs = sharedPrefHelper.loadFavorites()
        val allSongs = ArrayList<SongListWrapper>()
        val favoritesSongs = ArrayList<SongListWrapper>()
        songList?.forEach { song ->
            val isFavorite =
                savedFavoriteSongs.firstOrNull { it.songListResponse.id == song.id }?.isFavorite ?: false
            val wrappedSong = SongListWrapper(song, isFavorite)
            allSongs.add(wrappedSong)
            if (isFavorite) {
                favoritesSongs.add(wrappedSong)
            }
        }
        (allSongsLD as? MutableLiveData)?.postValue(allSongs)
        (favoritesSongsLD as? MutableLiveData)?.postValue(favoritesSongs)
    }

    fun updateFavorites(item: SongListWrapper) {
        item.changeFavoriteState()
        updateFavoritesList(item)
        updateAllSongsList()
    }

    private fun updateAllSongsList() {
        ArrayList<SongListWrapper>().apply {
            addAll(allSongsLD.value?: listOf())
            allSongsLD.value?.forEach { item ->
                item.isFavorite = sharedPrefHelper.loadFavorites()
                    .any { it.songListResponse.id == item.songListResponse.id }
            }
            (allSongsLD as MutableLiveData).postValue(this)
        }
    }

    private fun updateFavoritesList(item: SongListWrapper) {
        val isAlreadyFavorite =
            sharedPrefHelper.loadFavorites()
                .any { it.songListResponse.id == item.songListResponse.id }

        val updatedFavoriteList = ArrayList<SongListWrapper>()
        if (isAlreadyFavorite) {
            updatedFavoriteList.addAll(sharedPrefHelper.loadFavorites().filter { it.songListResponse.id != item.songListResponse.id })
        } else {
            updatedFavoriteList.addAll(sharedPrefHelper.loadFavorites())
            updatedFavoriteList.add(item)
        }
        sharedPrefHelper.saveFavorites(updatedFavoriteList)
        (favoritesSongsLD as? MutableLiveData)?.postValue(updatedFavoriteList)
    }

    fun volumeChange(item: SongListWrapper, progress: Int) {
        val itemId = item.songListResponse.id
        mediaPlayerMap[itemId]?.setVolume(progress)
    }

    fun stopAudio(item: SongListWrapper) {
        val itemId = item.songListResponse.id
        mediaPlayerMap[itemId]?.killMediaPlayer()
    }

    fun playAudio(
        item: SongListWrapper,
        button: View?,
        function: (View?) -> Unit
    ) {
        val itemId = item.songListResponse.id
        if (mediaPlayerMap[itemId] == null) {
            val mPlayer = PlayerHelper()
            mediaPlayerMap.put(itemId, mPlayer)
        }
        mediaPlayerMap[itemId]?.playAudio(app, item.songListResponse.fileAddress) {
            function(button)
        }
    }


}



