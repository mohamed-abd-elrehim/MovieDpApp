package com.example.banquemisrchallenge05movieapp.utils.data_layer.local.movielocaldp
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.banquemisrchallenge05movieapp.utils.shared_models.GenreTypeConverter
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieResultTypeConverter
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ProductionCompanyTypeConverter
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ProductionCountryTypeConverter
import com.example.banquemisrchallenge05movieapp.utils.shared_models.SpokenLanguageTypeConverter
import com.example.banquemisrchallenge05movieapp.utils.shared_models.StringListTypeConverter

@Database(entities = [
    MovieDbResultPopular::class,
    MovieDbResultUpcoming::class,
    MovieDbResultNowPlaying::class,
    MovieDetails::class], version = 1, exportSchema = false)
@TypeConverters(
    MovieResultTypeConverter::class,
    GenreTypeConverter::class,
    ProductionCompanyTypeConverter::class,
    ProductionCountryTypeConverter::class,
    SpokenLanguageTypeConverter::class,
    StringListTypeConverter::class
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

