package com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls

import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieResult

val fakePopularMoviesPage1 = MovieDbResultPopular(
    page = 1,
    total_pages = 2,
    total_results = 20,
    results = listOf(
        MovieResult(1, "en", "Popular Movie 1", "/path1.jpg", "2024-10-20", "Popular Movie 1", 7.2),
        MovieResult(2, "en", "Popular Movie 2", "/path2.jpg", "2024-09-15", "Popular Movie 2", 6.8),
        MovieResult(3, "en", "Popular Movie 3", "/path3.jpg", "2024-08-30", "Popular Movie 3", 8.1),
        MovieResult(4, "en", "Popular Movie 4", "/path4.jpg", "2024-07-25", "Popular Movie 4", 5.9),
        MovieResult(5, "en", "Popular Movie 5", "/path5.jpg", "2024-06-10", "Popular Movie 5", 7.6),
        MovieResult(6, "en", "Popular Movie 6", "/path6.jpg", "2024-05-05", "Popular Movie 6", 6.3),
        MovieResult(7, "en", "Popular Movie 7", "/path7.jpg", "2024-04-01", "Popular Movie 7", 7.9),
        MovieResult(8, "en", "Popular Movie 8", "/path8.jpg", "2024-03-20", "Popular Movie 8", 7.0),
        MovieResult(9, "en", "Popular Movie 9", "/path9.jpg", "2024-02-15", "Popular Movie 9", 8.3),
        MovieResult(10, "en", "Popular Movie 10", "/path10.jpg", "2024-01-10", "Popular Movie 10", 7.1)
    )
)

val fakePopularMoviesPage2 = MovieDbResultPopular(
    page = 2,
    total_pages = 2,
    total_results = 20,
    results = listOf(
        MovieResult(11, "en", "Popular Movie 11", "/path11.jpg", "2024-11-20", "Popular 11", 7.0),
        MovieResult(12, "en", "Popular Movie 12", "/path12.jpg", "2024-10-05", "Popular 12", 6.5),
        MovieResult(13, "en", "Popular Movie 13", "/path13.jpg", "2024-09-10", "Popular 13", 8.2),
        MovieResult(14, "en", "Popular Movie 14", "/path14.jpg", "2024-08-25", "Popular 14", 7.8),
        MovieResult(15, "en", "Popular Movie 15", "/path15.jpg", "2024-07-30", "Popular 15", 6.9),
        MovieResult(16, "en", "Popular Movie 16", "/path16.jpg", "2024-06-15", "Popular 16", 8.0),
        MovieResult(17, "en", "Popular Movie 17", "/path17.jpg", "2024-05-05", "Popular 17", 7.6),
        MovieResult(18, "en", "Popular Movie 18", "/path18.jpg", "2024-04-20", "Popular 18", 7.3),
        MovieResult(19, "en", "Popular Movie 19", "/path19.jpg", "2024-03-10", "Popular 19", 8.5),
        MovieResult(20, "en", "Popular Movie 20", "/path20.jpg", "2024-02-25", "Popular 20", 6.7)
    )
)

val  fakePopularMovies = mutableListOf(fakePopularMoviesPage1, fakePopularMoviesPage2)
