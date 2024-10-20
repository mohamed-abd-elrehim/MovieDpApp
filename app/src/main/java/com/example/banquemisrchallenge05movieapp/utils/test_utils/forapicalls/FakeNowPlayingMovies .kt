package com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls

import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieResult


val fakeNowPlayingMoviesPage1 = MovieDbResultNowPlaying(
    page = 1,
    total_pages = 2,
    total_results = 20,
    results = listOf(
        MovieResult(21, "en", "Now Playing Movie 21", "/now1.jpg", "2024-10-15", "Now Playing Movie 21", 8.4),
        MovieResult(22, "en", "Now Playing Movie 22", "/now2.jpg", "2024-09-30", "Now Playing Movie 22", 7.0),
        MovieResult(23, "en", "Now Playing Movie 23", "/now3.jpg", "2024-09-20", "Now Playing Movie 23", 7.7),
        MovieResult(24, "en", "Now Playing Movie 24", "/now4.jpg", "2024-09-10", "Now Playing Movie 24", 8.1),
        MovieResult(25, "en", "Now Playing Movie 25", "/now5.jpg", "2024-09-05", "Now Playing Movie 25", 7.6),
        MovieResult(26, "en", "Now Playing Movie 26", "/now6.jpg", "2024-08-30", "Now Playing Movie 26", 7.3),
        MovieResult(27, "en", "Now Playing Movie 27", "/now7.jpg", "2024-08-25", "Now Playing Movie 27", 8.2),
        MovieResult(28, "en", "Now Playing Movie 28", "/now8.jpg", "2024-08-20", "Now Playing Movie 28", 7.9),
        MovieResult(29, "en", "Now Playing Movie 29", "/now9.jpg", "2024-08-15", "Now Playing Movie 29", 6.5),
        MovieResult(30, "en", "Now Playing Movie 30", "/now10.jpg", "2024-08-10", "Now Playing Movie 30", 7.5)
    )
)
val fakeNowPlayingMoviesPage2 = MovieDbResultNowPlaying(
    page = 2,
    total_pages = 2,
    total_results = 20,
    results = listOf(
        MovieResult(31, "en", "Now Playing Movie 31", "/now11.jpg", "2024-10-25", "Now Playing Movie 31", 8.6),
        MovieResult(32, "en", "Now Playing Movie 32", "/now12.jpg", "2024-10-20", "Now Playing Movie 32", 7.9),
        MovieResult(33, "en", "Now Playing Movie 33", "/now13.jpg", "2024-10-15", "Now Playing Movie 33", 8.1),
        MovieResult(34, "en", "Now Playing Movie 34", "/now14.jpg", "2024-10-10", "Now Playing Movie 34", 7.4),
        MovieResult(35, "en", "Now Playing Movie 35", "/now15.jpg", "2024-10-05", "Now Playing Movie 35", 6.7),
        MovieResult(36, "en", "Now Playing Movie 36", "/now16.jpg", "2024-09-30", "Now Playing Movie 36", 7.3),
        MovieResult(37, "en", "Now Playing Movie 37", "/now17.jpg", "2024-09-25", "Now Playing Movie 37", 7.8),
        MovieResult(38, "en", "Now Playing Movie 38", "/now18.jpg", "2024-09-20", "Now Playing Movie 38", 8.4),
        MovieResult(39, "en", "Now Playing Movie 39", "/now19.jpg", "2024-09-15", "Now Playing Movie 39", 7.2),
        MovieResult(40, "en", "Now Playing Movie 40", "/now20.jpg", "2024-09-10", "Now Playing Movie 40", 7.1)
    )

)
val fakeNowPlayingMovies = mutableListOf( fakeNowPlayingMoviesPage1,fakeNowPlayingMoviesPage2)


