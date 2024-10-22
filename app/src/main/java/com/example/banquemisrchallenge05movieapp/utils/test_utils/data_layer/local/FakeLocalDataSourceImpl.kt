package com.example.banquemisrchallenge05movieapp.utils.data_layer.local

import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakeNowPlayingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakePopularMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakeUpcomingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.movieDetailsList
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forlocal.fakeLocalMoviesDetails

import com.example.banquemisrchallenge05movieapp.utils.test_utils.forlocal.fakeLocalNowPlayingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forlocal.fakeLocalPopularMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forlocal.fakeLocalUpcomingMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeLocalDataSourceImpl:LocalDataSource {
    override suspend fun insertMovieDbResultPopular(movie: MovieDbResultPopular) {

        val exists = fakeLocalPopularMovies.any { it.page == movie.page }
        if (exists) {
            fakeLocalPopularMovies.removeIf { it.page == movie.page }
            fakeLocalPopularMovies.add(movie)
        }else{
            fakeLocalPopularMovies.add(movie)
        }
    }

    override suspend fun insertMovieDbResultUpcoming(movie: MovieDbResultUpcoming) {
        val exists = fakeLocalUpcomingMovies.any { it.page == movie.page }
        if (exists) {
            fakeLocalUpcomingMovies.removeIf { it.page == movie.page }
            fakeLocalUpcomingMovies.add(movie)
        }else{
            fakeLocalUpcomingMovies.add(movie)
        }
    }

    override suspend fun insertMovieDbResultNowPlaying(movie: MovieDbResultNowPlaying) {
        val exists = fakeLocalNowPlayingMovies.any { it.page == movie.page }
        if (exists) {
            fakeLocalNowPlayingMovies.removeIf { it.page == movie.page }
            fakeLocalNowPlayingMovies.add(movie)
        }else{
            fakeLocalNowPlayingMovies.add(movie)
        }
    }

    override suspend fun insertMovieDetails(movie: MovieDetails) {
        val exists = fakeLocalMoviesDetails.any { it.id == movie.id }
        if (exists) {
            fakeLocalMoviesDetails.removeIf { it.id == movie.id }
            fakeLocalMoviesDetails.add(movie)
        }else{
            fakeLocalMoviesDetails.add(movie)
        }
    }






    override fun getPopularMovies(page: Int): Flow<MovieDbResultPopular?> {
        val movie = fakePopularMovies.find { it.page == page }
            ?: throw NoSuchElementException("Page $page not found")
        return flowOf(movie)
    }

    override fun getUpcomingMovies(page: Int): Flow<MovieDbResultUpcoming?> {
        val movie = fakeUpcomingMovies.find { it.page == page }
            ?: throw NoSuchElementException("Page $page not found")
        return flowOf(movie)
    }

    override fun getNowPlayingMovies(page: Int): Flow<MovieDbResultNowPlaying?> {
        val movie = fakeNowPlayingMovies.find { it.page == page }
            ?: throw NoSuchElementException("Page $page not found")
        return flowOf(movie)
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails?> {
        val movie = movieDetailsList.find { it.id == movieId }
            ?: throw NoSuchElementException("Movie with ID $movieId not found")
        return flowOf(movie)
    }
}