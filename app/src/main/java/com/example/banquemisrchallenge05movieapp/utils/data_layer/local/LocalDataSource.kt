package com.example.banquemisrchallenge05movieapp.utils.data_layer.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {

    suspend fun insertMovieDbResultPopular(movie: MovieDbResultPopular)
    suspend fun insertMovieDbResultUpcoming(movie: MovieDbResultUpcoming)
    suspend fun insertMovieDbResultNowPlaying(movie: MovieDbResultNowPlaying)
    suspend fun insertMovieDetails(movie: MovieDetails)

     fun getPopularMovies(page: Int): Flow<MovieDbResultPopular?>
     fun getUpcomingMovies(page: Int): Flow<MovieDbResultUpcoming?>
     fun getNowPlayingMovies(page: Int): Flow<MovieDbResultNowPlaying?>
     fun getMovieDetails(movieId: Int): Flow<MovieDetails?>

}


