package com.example.banquemisrchallenge05movieapp.utils.data_layer.remote

import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.moviedb.MoviedbAPIServices
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.moviedb.MoviedbRetrofitHelper
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class RemoteDataSourceImpl private constructor() : RemoteDataSource {

    private val apiService: MoviedbAPIServices = MoviedbRetrofitHelper.api

    companion object {
        @Volatile
        private var instance: RemoteDataSourceImpl? = null

        fun getInstance(): RemoteDataSourceImpl {
            return instance ?: synchronized(this) {
                instance ?: RemoteDataSourceImpl().also { instance = it }
            }
        }
    }

    override suspend fun fetchUpcomingMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultUpcoming> {
        return flowOf( apiService.fetchUpcomingMovies(language, page))
    }

    override suspend fun fetchPopularMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultPopular> {
        return flowOf( apiService.fetchPopularMovies(language, page))
    }

    override suspend fun fetchNowPlayingMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultNowPlaying> {
        return flowOf( apiService.fetchNowPlayingMovies(language, page))
    }

    override suspend fun fetchMovieDetails(movieId: Int): Flow<MovieDetails> {
        return flowOf( apiService.fetchMovieDetails(movieId))
    }



}