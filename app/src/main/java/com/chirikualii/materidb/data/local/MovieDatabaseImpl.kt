package com.chirikualii.materidb.data.local

import android.content.Context
import androidx.room.Room

class MovieDatabaseImpl(val context: Context) {
    private val db = Room.databaseBuilder(context, MovieDatabase::class.java, "movie.database").build()

    fun getDatabase(): MovieDatabase{
        return db
    }
}