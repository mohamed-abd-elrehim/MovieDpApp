package com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.moviedb

import com.example.banquemisrchallenge05movieapp.utils.constants.APIKeys
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviedbAPIServices {

    @GET(APIKeys.GET_UPCOMING_MOVIES_ENDPOINT)
    suspend fun fetchUpcomingMovies(
        @Query(APIKeys.LANGUAGE_PARAM) language: String,
        @Query(APIKeys.PAGE_PARAM) page: Int
    ): MovieDbResultUpcoming

    @GET(APIKeys.GET_POPULAR_MOVIES_ENDPOINT)
    suspend fun fetchPopularMovies(
        @Query(APIKeys.LANGUAGE_PARAM) language: String,
        @Query(APIKeys.PAGE_PARAM) page: Int
    ): MovieDbResultPopular

    @GET(APIKeys.GET_NOW_PLAYING_MOVIES_ENDPOINT)
    suspend fun fetchNowPlayingMovies(
        @Query(APIKeys.LANGUAGE_PARAM) language: String,
        @Query(APIKeys.PAGE_PARAM) page: Int
    ): MovieDbResultNowPlaying

    @GET(APIKeys.GET_MOVIE_DETAILS_ENDPOINT)
    suspend fun fetchMovieDetails(
        @Path(APIKeys.MOVIE_ID_PARAM) movieId: Int,
    ): MovieDetails





}