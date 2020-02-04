package com.base.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.base.model.retrofit.response.songListResponse.SongListResponse


@Dao
interface SongDao {
    @Insert
    suspend fun insertAll(songs : List<SongListResponse>): List<Long>

    @Query("SELECT * FROM songlistresponse")
    suspend fun getAllProducts(): List<SongListResponse>

    @Query("SELECT * FROM songlistresponse WHERE id = :songId")
    suspend fun getProduct(songId: Int) : SongListResponse

    @Query("DELETE FROM songlistresponse")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM songlistresponse ORDER BY ID")
    fun loadAllSongs(): LiveData<List<SongListResponse>>

}