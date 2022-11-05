package com.chirikualii.materidb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chirikualii.materidb.data.local.entity.MovieEntity
import com.chirikualii.materidb.data.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // onConflict memberitahu jika terjadi konflik pada ID
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movie WHERE typeMovie = (:typeMovie)")
    suspend fun getListMovieNowPlaying(
        typeMovie: String
    ): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE typeMovie = (:typeMovie)")
    suspend fun getListMoviePopular(
        typeMovie: String
    ): List<MovieEntity>
}