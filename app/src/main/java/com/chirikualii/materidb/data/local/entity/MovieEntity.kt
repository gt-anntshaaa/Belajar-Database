package com.chirikualii.materidb.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "movie")
data class MovieEntity(
    @PrimaryKey val movieId: String,
    @ColumnInfo(name = "title_movie" ) val title:String,
    @ColumnInfo(name = "releaseDate_movie") val releaseDate:String,
    @ColumnInfo(name = "imagePoster_movie") val imagePoster:String,
    @ColumnInfo(name = "backdrop_movie") val backdrop:String,
    @ColumnInfo(name= "overview_movie") val overview:String,

    @ColumnInfo(name= "typeMovie") val typeMovie: String
)
