package com.example.banquemisrchallenge05movieapp.utils.data_layer.local

import android.content.Context
import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.movielocaldp.MovieDatabase
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails


class LocalDataSourceImpl private constructor(context: Context) : LocalDataSource {
    private val db = MovieDatabase.getDatabase(context)
    private val movieDao = db.movieDao()


    companion object {
        @Volatile
        private var instance: LocalDataSourceImpl? = null

        fun getInstance(context: Context): LocalDataSourceImpl {
            return instance ?: synchronized(this) {
                instance ?: LocalDataSourceImpl(context).also { instance = it }
            }
        }
    }

    override suspend fun insertMovieDbResultPopular(movie: MovieDbResultPopular) {
        movieDao.insertMovieDbResultPopular(movie)
    }

    override suspend fun insertMovieDbResultUpcoming(movie: MovieDbResultUpcoming) {
        movieDao.insertMovieDbResultUpcoming(movie)
    }

    override suspend fun insertMovieDbResultNowPlaying(movie: MovieDbResultNowPlaying) {
        movieDao.insertMovieDbResultNowPlaying(movie)
    }

    override suspend fun insertMovieDetails(movie: MovieDetails) {
        movieDao.insertMovieDetails(movie)
    }

    override suspend fun getPopularMovies(page: Int): MovieDbResultPopular? {
        return movieDao.getPopularMovies(page)
    }

    override suspend fun getUpcomingMovies(page: Int): MovieDbResultUpcoming? {
        return movieDao.getUpcomingMovies(page)
    }

    override suspend fun getNowPlayingMovies(page: Int): MovieDbResultNowPlaying? {
        return movieDao.getNowPlayingMovies(page)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails? {
       return movieDao.getMovieDetails(movieId)
    }

}