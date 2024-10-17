package com.example.banquemisrchallenge05movieapp.utils.constants

import retrofit2.http.GET
import retrofit2.http.Query

object APIKeys {

    // Init setup
    const val MOVIEDB_API_KEY = "7396c8dfd064f9d442ae45269c103055"
    const val MOVIEDB_ACCESS_TOKEN ="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3Mzk2YzhkZmQwNjRmOWQ0NDJhZTQ1MjY5YzEwMzA1NSIsIm5iZiI6MTcyOTE5MTM4Ny40MDY0NzMsInN1YiI6IjY3MTE1NmJkYTJjZmUxMjVmYjk2MGE3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.t-4CPL0jH91yLcsq5KbbGWEcjtYs6iJMmOnMRJ8lRT4"
    const val AUTHORIZATION =  "Authorization"
    const val  MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie/"
    const val  MOVIEDB_IMAGE_URL = "https://image.tmdb.org/t/p/w500"


    //Endpoints
    const val GET_POPULAR_MOVIES_ENDPOINT = "popular"
    const val GET_UPCOMING_MOVIES_ENDPOINT = "upcoming"
    const val GET_NOW_PLAYING_MOVIES_ENDPOINT = "now_playing"

    const val GET_MOVIE_DETAILS_ENDPOINT = "{movie_id}"


    //EndPoints Params
    const val MOVIE_ID_PARAM = "movie_id"
    const val PAGE_PARAM = "page"
    const val LANGUAGE_PARAM = "language"
    const val INCLUDE_ADULT_PARAM = "include_adult"
    const val INCLUDE_VIDEO_PARAM = "include_video"
    const val REGION_PARAM = "region"




}




