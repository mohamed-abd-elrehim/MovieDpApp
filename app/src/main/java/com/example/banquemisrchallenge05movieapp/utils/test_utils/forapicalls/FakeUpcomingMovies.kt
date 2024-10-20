package com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls

import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieResult

val fakeUpcomingMoviesPage1 = MovieDbResultUpcoming(
    page = 1,
    total_pages = 2,
    total_results = 20,
    results = listOf(
        MovieResult(41, "en", "Upcoming Movie 41", "/upcoming1.jpg", "2024-12-20", "Upcoming Movie 41", 6.9),
        MovieResult(42, "en", "Upcoming Movie 42", "/upcoming2.jpg", "2024-12-15", "Upcoming Movie 42", 7.5),
        MovieResult(43, "en", "Upcoming Movie 43", "/upcoming3.jpg", "2024-12-10", "Upcoming Movie 43", 8.2),
        MovieResult(44, "en", "Upcoming Movie 44", "/upcoming4.jpg", "2024-12-05", "Upcoming Movie 44", 6.4),
        MovieResult(45, "en", "Upcoming Movie 45", "/upcoming5.jpg", "2024-12-01", "Upcoming Movie 45", 7.8),
        MovieResult(46, "en", "Upcoming Movie 46", "/upcoming6.jpg", "2024-11-25", "Upcoming Movie 46", 7.0),
        MovieResult(47, "en", "Upcoming Movie 47", "/upcoming7.jpg", "2024-11-20", "Upcoming Movie 47", 6.1),
        MovieResult(48, "en", "Upcoming Movie 48", "/upcoming8.jpg", "2024-11-15", "Upcoming Movie 48", 7.4),
        MovieResult(49, "en", "Upcoming Movie 49", "/upcoming9.jpg", "2024-11-10", "Upcoming Movie 49", 6.7),
        MovieResult(50, "en", "Upcoming Movie 50", "/upcoming10.jpg", "2024-11-05", "Upcoming Movie 50", 8.0)
    )
)
val fakeUpcomingMoviesPage2 = MovieDbResultUpcoming(
    page = 2,
    total_pages = 2,
    total_results = 20,
    results = listOf(
        MovieResult(51, "en", "Upcoming Movie 51", "/upcoming11.jpg", "2024-12-25", "Upcoming Movie 51", 7.1),
        MovieResult(52, "en", "Upcoming Movie 52", "/upcoming12.jpg", "2024-12-20", "Upcoming Movie 52", 6.8),
        MovieResult(53, "en", "Upcoming Movie 53", "/upcoming13.jpg", "2024-12-15", "Upcoming Movie 53", 8.3),
        MovieResult(54, "en", "Upcoming Movie 54", "/upcoming14.jpg", "2024-12-10", "Upcoming Movie 54", 7.5),
        MovieResult(55, "en", "Upcoming Movie 55", "/upcoming15.jpg", "2024-12-05", "Upcoming Movie 55", 6.4),
        MovieResult(56, "en", "Upcoming Movie 56", "/upcoming16.jpg", "2024-12-01", "Upcoming Movie 56", 7.0),
        MovieResult(57, "en", "Upcoming Movie 57", "/upcoming17.jpg", "2024-11-25", "Upcoming Movie 57", 8.0),
        MovieResult(58, "en", "Upcoming Movie 58", "/upcoming18.jpg", "2024-11-20", "Upcoming Movie 58", 6.9),
        MovieResult(59, "en", "Upcoming Movie 59", "/upcoming19.jpg", "2024-11-15", "Upcoming Movie 59", 7.6),
        MovieResult(60, "en", "Upcoming Movie 60", "/upcoming20.jpg", "2024-11-10", "Upcoming Movie 60", 7.4)
    )
)

val fakeUpcomingMovies = mutableListOf( fakeUpcomingMoviesPage1, fakeUpcomingMoviesPage2 )