package com.chirikualii.materidb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chirikualii.materidb.data.local.dao.MovieDao
import com.chirikualii.materidb.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao() : MovieDao
}