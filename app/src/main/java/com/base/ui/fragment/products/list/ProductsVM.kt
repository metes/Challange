package com.base.ui.fragment.products.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.base.base.BaseViewModel
import com.base.model.retrofit.response.songListResponse.SongListResponse
import com.base.repository.network.APIClient
import com.base.repository.room.SongDao
import com.base.repository.room.SongDatabase
import kotlinx.coroutines.launch


class ProductsVM(val app: Application, private val client: APIClient) : BaseViewModel(app, client) {

    private val songDao: SongDao by lazy { SongDatabase(app).songDao() }

    val allProductsLD: LiveData<List<SongListResponse>> = songDao.loadAllSongs()

    init {
        fetchSongs()
    }

    fun fetchSongs() {
        sendRequest({ client.songListClient() }, { newList ->
            viewModelScope.launch {
                newList?.let {
                    songDao.deleteAllProducts()
                    songDao.insertAll(it)
                }
            }
        })
    }





}

