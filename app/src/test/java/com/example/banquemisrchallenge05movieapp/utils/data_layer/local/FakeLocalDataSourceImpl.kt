package com.example.banquemisrchallenge05movieapp.utils.data_layer.local

import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forlocal.fakeLocalMoviesDetails

import com.example.banquemisrchallenge05movieapp.utils.test_utils.forlocal.fakeLocalNowPlayingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forlocal.fakeLocalPopularMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forlocal.fakeLocalUpcomingMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeLocalDataSourceImpl:LocalDataSource {
    override suspend fun insertMovieDbResultPopular(movie: MovieDbResultPopular) {
        fakeLocalPopularMovies.add(movie)
    }

    override suspend fun insertMovieDbResultUpcoming(movie: MovieDbResultUpcoming) {
        fakeLocalUpcomingMovies.add(movie)
    }

    override suspend fun insertMovieDbResultNowPlaying(movie: MovieDbResultNowPlaying) {
        fakeLocalNowPlayingMovies.add(movie)
    }

    override suspend fun insertMovieDetails(movie: MovieDetails) {
        fakeLocalMoviesDetails.add(movie)
    }

    override fun getPopularMovies(page: Int): Flow<MovieDbResultPopular?> {
        val movie = fakeLocalPopularMovies.find { it.page == page }
            ?: throw NoSuchElementException("Page $page not found")
        return flowOf(movie)
    }

    override fun getUpcomingMovies(page: Int): Flow<MovieDbResultUpcoming?> {
        val movie = fakeLocalUpcomingMovies.find { it.page == page }
            ?: throw NoSuchElementException("Page $page not found")
        return flowOf(movie)
    }

    override fun getNowPlayingMovies(page: Int): Flow<MovieDbResultNowPlaying?> {
        val movie = fakeLocalNowPlayingMovies.find { it.page == page }
            ?: throw NoSuchElementException("Page $page not found")
        return flowOf(movie)
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails?> {
        val movie = fakeLocalMoviesDetails.find { it.id == movieId }
            ?: throw NoSuchElementException("Movie with ID $movieId not found")
        return flowOf(movie)
    }
}