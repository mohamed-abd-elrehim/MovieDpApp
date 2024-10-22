package com.example.banquemisrchallenge05movieapp.utils.data_layer.remote

import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakeNowPlayingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakePopularMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakeUpcomingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.movieDetailsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRemoteDataSourceImpl: RemoteDataSource {
    var emptyMovies= false
    var networkError = false

    override suspend fun fetchUpcomingMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultUpcoming> {
        if (networkError) {
            throw Exception("Network Error")
        }
        if (emptyMovies) {
            throw Exception("List is empty")
        }
        val movieDbResultUpcoming = fakeUpcomingMovies.find { it.page == page }
            ?: throw Exception("Page $page not found")
        return flowOf(movieDbResultUpcoming)

    }

    override suspend fun fetchPopularMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultPopular> {
        if (networkError) {
            throw Exception("Network Error")
        }
        if (emptyMovies) {
            throw Exception("List is empty")
        }
        val movieDbResultPopular = fakePopularMovies.find { it.page == page }
            ?: throw Exception("Page $page not found")
        return flowOf(movieDbResultPopular)
    }

    override suspend fun fetchNowPlayingMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultNowPlaying> {
        if (networkError) {
            throw Exception("Network Error")
        }
        if (emptyMovies) {
            throw Exception("List is empty")
        }
        val movieDbResultNowPlaying = fakeNowPlayingMovies.find { it.page == page }
            ?: throw Exception("Page $page not found")
        return flowOf(movieDbResultNowPlaying)
    }

    override suspend fun fetchMovieDetails(movieId: Int): Flow<MovieDetails> {
        if (networkError) {
            throw Exception("Network Error")
        }
        if (emptyMovies) {
            throw Exception("List is empty")
        }
        val movieDetails = movieDetailsList.find { it.id == movieId }
            ?: throw Exception("Movie with ID $movieId not found")

        return flowOf(movieDetails)
    }
}