package com.example.banquemisrchallenge05movieapp.utils.shared_models
import androidx.room.*
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Movie List
@Entity(tableName = "movie_db_result_popular")
data class MovieDbResultPopular(
    @PrimaryKey val page: Int,
    val total_pages: Int,
    val total_results: Int,
    @TypeConverters(MovieResultTypeConverter::class)
    val results: List<MovieResult>
)

@Entity(tableName = "movie_db_result_upcoming")
data class MovieDbResultUpcoming(
    @PrimaryKey val page: Int,
    val total_pages: Int,
    val total_results: Int,
    @TypeConverters(MovieResultTypeConverter::class)
    val results: List<MovieResult>
)

@Entity(tableName = "movie_db_result_now_playing")
data class MovieDbResultNowPlaying(
    @PrimaryKey val page: Int,
    val total_pages: Int,
    val total_results: Int,
    @TypeConverters(MovieResultTypeConverter::class)
    val results: List<MovieResult>
)

@Entity(tableName = "movie_details")
data class MovieDetails(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Int,
    @TypeConverters(GenreTypeConverter::class)
    val genres: List<Genre>,
    val homepage: String?,
    val imdb_id: String?,
    @TypeConverters(StringListTypeConverter::class)
    val origin_country: List<String>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    @TypeConverters(ProductionCompanyTypeConverter::class)
    val production_companies: List<ProductionCompany>,
    @TypeConverters(ProductionCountryTypeConverter::class)
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Long,
    val runtime: Int?,
    @TypeConverters(SpokenLanguageTypeConverter::class)
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

data class MovieResult(
    val id: Int,
    val original_language: String,
    val original_title: String,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val vote_average: Double,
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val logo_path: String?,
    val name: String,
    val origin_country: String
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)

data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)




class MovieResultTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMovieResultList(results: List<MovieResult>?): String? {
        return gson.toJson(results)
    }

    @TypeConverter
    fun toMovieResultList(resultString: String?): List<MovieResult>? {
        if (resultString == null) {
            return null
        }
        val type = object : TypeToken<List<MovieResult>>() {}.type
        return gson.fromJson(resultString, type)
    }
}

class GenreTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String? {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenreList(genreString: String?): List<Genre>? {
        if (genreString == null) {
            return null
        }
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(genreString, type)
    }
}

class ProductionCompanyTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromProductionCompanyList(companies: List<ProductionCompany>?): String? {
        return gson.toJson(companies)
    }

    @TypeConverter
    fun toProductionCompanyList(companyString: String?): List<ProductionCompany>? {
        if (companyString == null) {
            return null
        }
        val type = object : TypeToken<List<ProductionCompany>>() {}.type
        return gson.fromJson(companyString, type)
    }
}

class ProductionCountryTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromProductionCountryList(countries: List<ProductionCountry>?): String? {
        return gson.toJson(countries)
    }

    @TypeConverter
    fun toProductionCountryList(countryString: String?): List<ProductionCountry>? {
        if (countryString == null) {
            return null
        }
        val type = object : TypeToken<List<ProductionCountry>>() {}.type
        return gson.fromJson(countryString, type)
    }
}

class SpokenLanguageTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSpokenLanguageList(languages: List<SpokenLanguage>?): String? {
        return gson.toJson(languages)
    }

    @TypeConverter
    fun toSpokenLanguageList(languageString: String?): List<SpokenLanguage>? {
        if (languageString == null) {
            return null
        }
        val type = object : TypeToken<List<SpokenLanguage>>() {}.type
        return gson.fromJson(languageString, type)
    }
}

class StringListTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(strings: List<String>?): String? {
        return gson.toJson(strings)
    }

    @TypeConverter
    fun toStringList(stringString: String?): List<String>? {
        if (stringString == null) {
            return null
        }
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(stringString, type)
    }
}

