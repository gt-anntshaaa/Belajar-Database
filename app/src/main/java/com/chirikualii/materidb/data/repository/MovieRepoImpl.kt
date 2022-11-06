package com.chirikualii.materidb.data.repository

import android.util.Log
import com.chirikualii.materidb.data.local.MovieDatabase
import com.chirikualii.materidb.data.local.MovieDatabaseImpl
import com.chirikualii.materidb.data.local.entity.MovieEntity
import com.chirikualii.materidb.data.model.Movie
import com.chirikualii.materidb.data.model.MovieType
import com.chirikualii.materidb.data.remote.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieRepoImpl(private val service: ApiService, private val movieDbImpl: MovieDatabaseImpl): MovieRepo {

    // insert data from API to database
    override suspend fun getPopularMovie(): List<Movie> {
       try {
           val response = service.getPopularMovie()

           if(response.isSuccessful){
               val listMovie = response.body()
//               val listData = listMovie?.results?.map {
//                   Movie(
//                       title = it.title,
//                       releaseDate = it.releaseDate,
//                       imagePoster = it.posterPath,
//                       overview = it.overview,
//                       backdrop = it.backdropPath
//                   )
//               }
               val listMovieEntity = listMovie?.results?.map {
                   MovieEntity(
                       movieId = it.id.toString(),
                       title =  it.title,
                       releaseDate = it.releaseDate,
                       imagePoster = it.posterPath,
                       overview = it.overview,
                       backdrop = it.backdropPath,
                       typeMovie = MovieType.POPULAR
                   )
               }

               // insert to db
               listMovieEntity?.forEach {
                    movieDbImpl.getDatabase().movieDao().insertMovie(it)
               }

               Log.d(MovieRepoImpl::class.simpleName,
                   "getPopularMovie : ${Gson().toJsonTree(listMovieEntity)}")
               return getPopularMovieLocal() ?: emptyList()
           }else{
               Log.e(MovieRepoImpl::class.simpleName,
                   "getPopularMovie error code: ${response.code()}", )
               return getPopularMovieLocal()
           }
       }catch (e:Exception){
           Log.e(MovieRepoImpl::class.simpleName, "getPopularMovie error :${e.message} ", )
           return getPopularMovieLocal()
       }
    }

    override suspend fun getNowPlayingMovie(): List<Movie> {
        try {
            val response = service.getNowPlayingMovie()

            if(response.isSuccessful){
                val listMovie = response.body()
//                val listData = listMovie?.results?.map {
//                    Movie(
//                        title = it.title,
//                        releaseDate = it.releaseDate,
//                        imagePoster = it.posterPath,
//                        overview = it.overview,
//                        backdrop = it.backdropPath
//                    )
//                }

                val listMovieEntity = listMovie?.results?.map {
                    MovieEntity(
                        movieId = it.id.toString(),
                        title =  it.title,
                        releaseDate = it.releaseDate,
                        imagePoster = it.posterPath,
                        overview = it.overview,
                        backdrop = it.backdropPath,
                        typeMovie = MovieType.NOW_PLAYING
                    )
                }

                listMovieEntity?.forEach {
                    movieDbImpl.getDatabase().movieDao().insertMovie(it)
                }

                Log.d(MovieRepoImpl::class.simpleName,
                    "getNowPlayingMovie : ${Gson().toJsonTree(listMovieEntity)}")
                return getNowPlayingMovieLocal()
            }else{
                Log.e(MovieRepoImpl::class.simpleName,
                    "getNowPlayingMovie error code: ${response.code()}", )
                return getNowPlayingMovieLocal()
            }
        }catch (e:Exception){
            Log.e(MovieRepoImpl::class.simpleName, "getNowPlayingMovie error :${e.message} ", )
            return getNowPlayingMovieLocal()
        }
    }

    // insert to database
    override suspend fun getPopularMovieLocal(): List<Movie> {
        var listData = listOf<Movie>()
       try{
           listData = movieDbImpl.getDatabase().movieDao().getListMoviePopular(
               MovieType.POPULAR
           ).map { // -> Map : mengconvert Type data
               Movie(
                   movieId = it.movieId,
                   title = it.title,
                   releaseDate = it.releaseDate,
                   imagePoster = it.imagePoster,
                   backdrop = it.backdrop,
                   overview = it.overview
               )
           }
           return listData
       }catch (e: Exception){
           Log.e("MovieRepoImpl::class.simpleName", "getPopularMovieLocal: error ${e.message}")
           return listData
       }
    }

    override suspend fun getNowPlayingMovieLocal(): List<Movie> {
        var listData = listOf<Movie>()
        try {
            listData = movieDbImpl.getDatabase().movieDao().getListMovieNowPlaying(
                MovieType.NOW_PLAYING
            ).map {
                Movie(
                    movieId = it.movieId,
                    title = it.title,
                    releaseDate = it.releaseDate,
                    imagePoster = it.imagePoster,
                    backdrop = it.backdrop,
                    overview = it.overview
                )
            }
            return listData
        }catch (e: Exception){
            Log.e("MovieRepoImpl::class.simpleName", "getNowPlayingMovieLocal: error ${e.message}", )
            return listData
        }
    }
}