package com.example.banquemisrchallenge05movieapp.utils.data_layer.remote

import com.example.banquemisrchallenge05movieapp.utils.constants.APIKeys
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RemoteDataSource {

    suspend fun fetchUpcomingMovies(
        language: String = "en-US",
        page: Int = 1
    ): Flow<MovieDbResultUpcoming>


    suspend fun fetchPopularMovies(
        language: String = "en-US",
        page: Int = 1
    ): Flow<MovieDbResultPopular>

    suspend fun fetchNowPlayingMovies(
        language: String = "en-US",
        page: Int = 1
    ): Flow<MovieDbResultNowPlaying>

    suspend fun fetchMovieDetails(
      movieId: Int
    ): Flow<MovieDetails>

}


