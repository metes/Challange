package com.base.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.base.model.retrofit.response.songListResponse.SongListResponse

@Database(entities = [SongListResponse::class], version = 1 )
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao() : SongDao

    companion object {
        @Volatile private var instance : SongDatabase? = null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            SongDatabase::class.java,
            "myDatabase"
        ).build()
    }
}
