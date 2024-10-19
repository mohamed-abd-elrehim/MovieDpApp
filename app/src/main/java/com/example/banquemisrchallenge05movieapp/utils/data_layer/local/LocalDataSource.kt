package com.example.banquemisrchallenge05movieapp.utils.data_layer.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails


interface LocalDataSource {

    suspend fun insertMovieDbResultPopular(movie: MovieDbResultPopular)
    suspend fun insertMovieDbResultUpcoming(movie: MovieDbResultUpcoming)
    suspend fun insertMovieDbResultNowPlaying(movie: MovieDbResultNowPlaying)
    suspend fun insertMovieDetails(movie: MovieDetails)

    suspend fun getPopularMovies(page: Int): MovieDbResultPopular?
    suspend fun getUpcomingMovies(page: Int): MovieDbResultUpcoming?
    suspend fun getNowPlayingMovies(page: Int): MovieDbResultNowPlaying?
    suspend fun getMovieDetails(movieId: Int): MovieDetails?

}


