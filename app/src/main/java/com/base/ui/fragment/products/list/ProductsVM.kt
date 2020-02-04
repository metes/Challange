package com.base.ui.fragment.products.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.base.base.BaseViewModel
import com.base.model.retrofit.response.songListResponse.SongListResponse
import com.base.repository.network.APIClient
import com.base.repository.room.SongDatabase
import kotlinx.coroutines.launch


class ProductsVM(val app: Application, val client: APIClient) : BaseViewModel(app, client) {

    private val songDatabase: SongDatabase by lazy { SongDatabase(app) }

    val allSongsLD: LiveData<List<SongListResponse>> = prepareAllSongsLD()

    init {
        prepareAllSongsLD()
        fetchSongs()
    }

    private fun prepareAllSongsLD(): LiveData<List<SongListResponse>> {
        if (songDatabase.songDao().loadAllSongs().value.isNullOrEmpty()) {
            (allSongsLD as MutableLiveData).value = arrayListOf()
        }
        return songDatabase.songDao().loadAllSongs()
    }

    fun fetchSongs() {
        sendRequest({ client.songListClient() }, { newList ->
            viewModelScope.launch {
                newList?.let { songDatabase.songDao().insertAll(it) }
            }
        })
    }





}

