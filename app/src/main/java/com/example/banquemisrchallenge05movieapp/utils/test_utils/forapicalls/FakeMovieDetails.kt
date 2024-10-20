package com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls

import com.example.banquemisrchallenge05movieapp.utils.shared_models.Genre
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ProductionCompany
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ProductionCountry
import com.example.banquemisrchallenge05movieapp.utils.shared_models.SpokenLanguage

val movieDetailsList = (1..60).map { id ->
    val (title, originalTitle) = when {
        id in 1..20 -> "Popular Movie $id" to "Popular Movie $id"
        id in 21..40 -> "Now Playing Movie $id" to "Now Playing Movie $id"
        id in 41..60 -> "Upcoming Movie $id" to "Upcoming Movie $id"
        else -> "Unknown" to "Unknown"
    }

    MovieDetails(
        id = id,
        adult = false,
        backdrop_path = "/backdrop$id.jpg",
        budget = 100000000 + id * 50000,  // Sample budget logic
        genres = listOf(Genre(28, "Action"), Genre(12, "Adventure")),  // Same genres for simplicity
        homepage = "https://www.moviedb$id.com",
        imdb_id = "tt12345$id",
        origin_country = listOf("US"),
        original_language = "en",
        original_title = originalTitle,
        overview = "Overview of $title. This is a description of the movie.",
        popularity = 100.0 + id * 5,  // Sample popularity logic
        poster_path = "/poster$id.jpg",
        production_companies = listOf(
            ProductionCompany(1, "/logo1.png", "Company $id", "US")
        ),
        production_countries = listOf(ProductionCountry("US", "United States")),
        release_date = "2024-07-${10 + (id % 20)}",  // Sample release date logic
        revenue = 500000000L + id * 10000,  // Sample revenue logic
        runtime = 120 + (id % 30),  // Sample runtime logic
        spoken_languages = listOf(SpokenLanguage("English", "en", "English")),
        status = if (id in 1..40) "Released" else "Post-Production",  // Based on movie category
        tagline = "Tagline for $title",
        title = title,
        video = false,
        vote_average = 7.0 + (id % 5) * 0.1,  // Sample vote average logic
        vote_count = 1000 + id * 50  // Sample vote count logic
    )
}
