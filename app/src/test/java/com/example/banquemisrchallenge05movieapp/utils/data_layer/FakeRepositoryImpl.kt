package com.example.banquemisrchallenge05movieapp.utils.data_layer

import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import kotlinx.coroutines.flow.Flow

class FakeRepositoryImpl : Repository {
    override suspend fun fetchUpcomingMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultUpcoming> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPopularMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultPopular> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNowPlayingMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultNowPlaying> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMovieDetails(movieId: Int): Flow<MovieDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovieDbResultPopular(movie: MovieDbResultPopular) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovieDbResultUpcoming(movie: MovieDbResultUpcoming) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovieDbResultNowPlaying(movie: MovieDbResultNowPlaying) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovieDetails(movie: MovieDetails) {
        TODO("Not yet implemented")
    }

    override fun getPopularMovies(page: Int): Flow<MovieDbResultPopular?> {
        TODO("Not yet implemented")
    }

    override fun getUpcomingMovies(page: Int): Flow<MovieDbResultUpcoming?> {
        TODO("Not yet implemented")
    }

    override fun getNowPlayingMovies(page: Int): Flow<MovieDbResultNowPlaying?> {
        TODO("Not yet implemented")
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails?> {
        TODO("Not yet implemented")
    }
}