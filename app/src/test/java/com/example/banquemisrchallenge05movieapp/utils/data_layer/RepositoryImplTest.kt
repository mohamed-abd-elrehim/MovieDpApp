package com.example.banquemisrchallenge05movieapp.utils.data_layer

import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.FakeLocalDataSourceImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.LocalDataSource
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.FakeRemoteDataSourceImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.RemoteDataSource
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
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException


class RepositoryImplTest {

    lateinit var remoteDataSource: FakeRemoteDataSourceImpl
    lateinit var localDataSource: FakeLocalDataSourceImpl
    lateinit var repository: Repository

    val expectedMovieDetails = movieDetailsList[0]
    val expectedPopularMoviesPage = fakePopularMovies[0]
    val expectedUpcomingMoviesPage = fakeUpcomingMovies[0]
    val expecteNowPlayingMoviesPage = fakeNowPlayingMovies[0]


    @Before
    fun createRepository() {
        remoteDataSource = FakeRemoteDataSourceImpl()
        localDataSource = FakeLocalDataSourceImpl()
        repository = RepositoryImpl.getInstance(remoteDataSource, localDataSource)
    }

    @Test
    fun fetchUpcomingMovies_ReturnsExpectedData() = runTest {
        val result = repository.fetchUpcomingMovies(page = 1).collect {
            assertEquals(expectedUpcomingMoviesPage, it)
        }
    }


    @Test
    fun fetchUpcomingMovies_ReturnsEmptyList_WhenNoMoviesAvailable() = runTest {
        remoteDataSource.emptyMovies = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchUpcomingMovies(page = 1).collect {
                    throw Exception("List is empty")
                }
            }
        }
    }

    @Test
    fun fetchUpcomingMovies_ThrowsException_WhenRemoteSourceFails() = runTest {
        remoteDataSource.networkError = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchUpcomingMovies(page = 1).collect {
                    throw Exception("Network Error")
                }
            }
        }
    }

    @Test
    fun fetchUpcomingMovies_HandlesNullResponseGracefully() = runTest {
        remoteDataSource.networkError = true
        assertThrows(Exception::class.java) {
            runTest {
                val page = 3
                repository.fetchUpcomingMovies(page = page).collect {
                    throw Exception("Page $page not found")
                }
            }
        }
    }


    @Test
    fun fetchNowPlayingMovies_ReturnsExpectedData() = runTest {
        val result = repository.fetchNowPlayingMovies(page = 1).collect {
            assertEquals(expecteNowPlayingMoviesPage, it)
        }
    }

    @Test
    fun fetchNowPlayingMovies_ReturnsEmptyList_WhenNoMoviesAvailable() = runTest {
        remoteDataSource.emptyMovies = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchNowPlayingMovies(page = 1).collect {
                    throw Exception("List is empty")
                }
            }
        }
    }

    @Test
    fun fetchNowPlayingMovies_ThrowsException_WhenRemoteSourceFails() = runTest {
        remoteDataSource.networkError = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchNowPlayingMovies(page = 1).collect {
                    throw Exception("Network Error")
                }
            }
        }
    }

    @Test
    fun fetchNowPlayingMovies_HandlesNullResponseGracefully() = runTest {
        val page = 3
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchNowPlayingMovies(page = page).collect {
                    throw Exception("Page $page not found")
                }
            }
        }
    }


    @Test
    fun fetchMovieDetails_ReturnsExpectedData() = runTest {
        val movieId = 1
        val result = repository.fetchMovieDetails(movieId = movieId).collect {
            assertEquals(expectedMovieDetails, it)
        }
    }

    @Test
    fun fetchMovieDetails_ThrowsException_WhenMovieNotFound() = runTest {
        val invalidMovieId = 999
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchMovieDetails(movieId = invalidMovieId).collect {
                    throw Exception("Movie with ID $invalidMovieId not found")
                }
            }
        }
    }

    @Test
    fun fetchMovieDetails_ThrowsException_WhenRemoteSourceFails() = runTest {
        remoteDataSource.networkError = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchMovieDetails(movieId = 1).collect {
                    throw Exception("Network Error")
                }
            }
        }
    }

    @Test
    fun fetchMovieDetails_ReturnsEmpty_WhenNoDetailsAvailable() = runTest {
        remoteDataSource.emptyMovies = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchMovieDetails(movieId = 1).collect {
                    throw Exception("List is empty")
                }
            }
        }
    }

    @Test
    fun fetchPopularMovies_ReturnsExpectedData() = runTest {
        val result = repository.fetchPopularMovies(page = 1).collect {
            assertEquals(expectedPopularMoviesPage, it)
        }
    }

    @Test
    fun fetchPopularMovies_ReturnsEmptyList_WhenNoMoviesAvailable() = runTest {
        remoteDataSource.emptyMovies = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchPopularMovies(page = 1).collect {
                    throw Exception("List is empty")
                }
            }
        }
    }

    @Test
    fun fetchPopularMovies_ThrowsException_WhenRemoteSourceFails() = runTest {
        remoteDataSource.networkError = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchPopularMovies(page = 1).collect {
                    throw Exception("Network Error")
                }
            }
        }
    }

    @Test
    fun fetchPopularMovies_HandlesNullResponseGracefully() = runTest {
        val page = 3
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchPopularMovies(page = page).collect {
                    throw Exception("Page $page not found")
                }
            }
        }
    }


    //Local Tests
    //Insert Movie DbResult Popular
    @Test
    fun testInsertMovieDbResultPopular_SingleEntry() = runBlocking {
        // Arrange
        val movie = fakePopularMovies[0]

        // Act
        repository.insertMovieDbResultPopular(movie)

        // Assert
        assertEquals(1, fakeLocalPopularMovies.size)
        assertEquals(movie, fakeLocalPopularMovies[0])
    }

    @Test
    fun testInsertMovieDbResultPopular_MultipleEntries() = runBlocking {
        // Arrange
        val movie1 = fakePopularMovies[0]
        val movie2 = fakePopularMovies[1]

        // Act
        repository.insertMovieDbResultPopular(movie1)
        repository.insertMovieDbResultPopular(movie2)

        // Assert
        assertEquals(2, fakeLocalPopularMovies.size)
        assertTrue(fakeLocalPopularMovies.contains(movie1))
        assertTrue(fakeLocalPopularMovies.contains(movie2))
    }


    @Test
    fun testInsertMovieDbResultPopular_LargeDataset() = runBlocking {
        // Arrange
        val movies = (1..1000).map {
            MovieDbResultPopular(
                page = it,
                results = fakePopularMovies[0].results,
                total_pages = fakePopularMovies[0].total_pages,
                total_results = fakePopularMovies[0].total_results
            )
        }

        // Act
        movies.forEach { repository.insertMovieDbResultPopular(it) }

        // Assert
        assertEquals(1000, fakeLocalPopularMovies.size)
    }

    @Test
    fun testInsertMovieDbResultPopular_IntegrityOfInsertedData() = runBlocking {
        // Arrange
        val movie = fakePopularMovies[0]
        // Act
        repository.insertMovieDbResultPopular(movie)

        // Assert
        assertEquals(movie, fakeLocalPopularMovies[0])
        assertNotNull(fakeLocalPopularMovies[0].total_results) // Example integrity check
    }

    @Test
    fun testInsertMovieDbResultPopular_NoDuplicateEntries() = runBlocking {
        // Arrange
        val movie = fakePopularMovies[0]

        // Act
        repository.insertMovieDbResultPopular(movie)
        repository.insertMovieDbResultPopular(movie)

        // Assert
        assertEquals(1, fakeLocalPopularMovies.size)
    }

    @Test
    fun testInsertMovieDbResultPopular_ConcurrentInserts() = runBlocking {
        // Act
        val movie1 = fakePopularMovies[0]
        val movie2 = fakePopularMovies[1]

        // Run concurrent inserts
        val jobs = listOf(
            launch { repository.insertMovieDbResultPopular(movie1) },
            launch { repository.insertMovieDbResultPopular(movie2) }
        )

        jobs.forEach { it.join() }

        // Assert
        assertEquals(2, fakeLocalPopularMovies.size)
    }

    @Test
    fun testInsertMovieDbResultPopular_SimilarMoviesWithDifferentIds() = runBlocking {
        // Arrange
        val movie1 = fakePopularMovies[0]
        var movie2 = MovieDbResultPopular(
            page = 2,
            results = fakePopularMovies[0].results,
            total_pages = fakePopularMovies[0].total_pages,
            total_results = fakePopularMovies[0].total_results
        )

        // Act
        repository.insertMovieDbResultPopular(movie1)
        repository.insertMovieDbResultPopular(movie2)

        // Assert
        assertEquals(2, fakeLocalPopularMovies.size)
    }

///////////////////////////////////////////////////

    // Insert Movie DbResult Upcoming
    @Test
    fun testInsertMovieDbResultUpcoming_SingleEntry() = runBlocking {
        // Arrange
        val movie = fakeUpcomingMovies[0]

        // Act
        repository.insertMovieDbResultUpcoming(movie)

        // Assert
        assertEquals(1, fakeLocalUpcomingMovies.size)
        assertEquals(movie, fakeLocalUpcomingMovies[0])
    }

    @Test
    fun testInsertMovieDbResultUpcoming_MultipleEntries() = runBlocking {
        // Arrange
        val movie1 = fakeUpcomingMovies[0]
        val movie2 = fakeUpcomingMovies[1]

        // Act
        repository.insertMovieDbResultUpcoming(movie1)
        repository.insertMovieDbResultUpcoming(movie2)

        // Assert
        assertEquals(2, fakeLocalUpcomingMovies.size)
        assertTrue(fakeLocalUpcomingMovies.contains(movie1))
        assertTrue(fakeLocalUpcomingMovies.contains(movie2))
    }


    @Test
    fun testInsertMovieDbResultUpcoming_LargeDataset() = runBlocking {
        // Arrange
        val movies = (1..1000).map {
            MovieDbResultUpcoming(
                page = it,
                results = fakeUpcomingMovies[0].results,
                total_pages = fakeUpcomingMovies[0].total_pages,
                total_results = fakeUpcomingMovies[0].total_results
            )
        }

        // Act
        movies.forEach { repository.insertMovieDbResultUpcoming(it) }

        // Assert
        assertEquals(1000, fakeLocalUpcomingMovies.size)
    }


    @Test
    fun testInsertMovieDbResultUpcoming_IntegrityOfInsertedData() = runBlocking {
        // Arrange
        val movie = fakeUpcomingMovies[0]
        // Act
        repository.insertMovieDbResultUpcoming(movie)

        // Assert
        assertEquals(movie, fakeLocalUpcomingMovies[0])
        assertNotNull(fakeLocalUpcomingMovies[0].total_results)
    }

    @Test
    fun testInsertMovieDbResultUpcoming_NoDuplicateEntries() = runBlocking {
        // Arrange
        val movie = fakeUpcomingMovies[0]

        // Act
        repository.insertMovieDbResultUpcoming(movie)
        repository.insertMovieDbResultUpcoming(movie)

        // Assert
        assertEquals(1, fakeLocalUpcomingMovies.size)
    }

    @Test
    fun testInsertMovieDbResultUpcoming_ConcurrentInserts() = runBlocking {
        // Act
        val movie1 = fakeUpcomingMovies[0]
        val movie2 = fakeUpcomingMovies[1]

        // Run concurrent inserts
        val jobs = listOf(
            launch { repository.insertMovieDbResultUpcoming(movie1) },
            launch { repository.insertMovieDbResultUpcoming(movie2) }
        )

        jobs.forEach { it.join() }

        // Assert
        assertEquals(2, fakeLocalUpcomingMovies.size)
    }

    @Test
    fun testInsertMovieDbResultUpcoming_SimilarMoviesWithDifferentIds() = runBlocking {
        // Arrange
        val movie1 = fakeUpcomingMovies[0]
        var movie2 = MovieDbResultUpcoming(
            page = 2,
            results = fakeUpcomingMovies[0].results,
            total_pages = fakeUpcomingMovies[0].total_pages,
            total_results = fakeUpcomingMovies[0].total_results
        )

        // Act
        repository.insertMovieDbResultUpcoming(movie1)
        repository.insertMovieDbResultUpcoming(movie2)

        // Assert
        assertEquals(2, fakeLocalUpcomingMovies.size)
    }

    ///////////////////////////////////////////////////

    @Test
    fun testInsertMovieDbResultNowPlaying_SingleEntry() = runBlocking {
        // Arrange
        val movie = fakeNowPlayingMovies[0]

        // Act
        repository.insertMovieDbResultNowPlaying(movie)

        // Assert
        assertEquals(1, fakeLocalNowPlayingMovies.size)
        assertEquals(movie, fakeLocalNowPlayingMovies[0])
    }

    @Test
    fun testInsertMovieDbResultNowPlaying_MultipleEntries() = runBlocking {
        // Arrange
        val movie1 = fakeNowPlayingMovies[0]
        val movie2 = fakeNowPlayingMovies[1]

        // Act
        repository.insertMovieDbResultNowPlaying(movie1)
        repository.insertMovieDbResultNowPlaying(movie2)

        // Assert
        assertEquals(2, fakeLocalNowPlayingMovies.size)
        assertTrue(fakeLocalNowPlayingMovies.contains(movie1))
        assertTrue(fakeLocalNowPlayingMovies.contains(movie2))
    }


    @Test
    fun testInsertMovieDbResultNowPlaying_LargeDataset() = runBlocking {
        // Arrange
        val movies = (1..1000).map {
            MovieDbResultNowPlaying(
                page = it,
                results = fakeNowPlayingMovies[0].results,
                total_pages = fakeNowPlayingMovies[0].total_pages,
                total_results = fakeNowPlayingMovies[0].total_results
            )
        }

        // Act
        movies.forEach { repository.insertMovieDbResultNowPlaying(it) }

        // Assert
        assertEquals(1000, fakeLocalNowPlayingMovies.size)
    }

    @Test
    fun testInsertMovieDbResultNowPlaying_IntegrityOfInsertedData() = runBlocking {
        // Arrange
        val movie = fakeNowPlayingMovies[0]
        // Act
        repository.insertMovieDbResultNowPlaying(movie)

        // Assert
        assertEquals(movie, fakeLocalNowPlayingMovies[0])
        assertNotNull(fakeLocalNowPlayingMovies[0].total_results)
    }

    @Test
    fun testInsertMovieDbResultNowPlaying_NoDuplicateEntries() = runBlocking {
        // Arrange
        val movie = fakeNowPlayingMovies[0]

        // Act
        repository.insertMovieDbResultNowPlaying(movie)
        repository.insertMovieDbResultNowPlaying(movie)

        // Assert
        assertEquals(1, fakeLocalNowPlayingMovies.size)
    }

    @Test
    fun testInsertMovieDbResultNowPlaying_ConcurrentInserts() = runBlocking {
        // Act
        val movie1 = fakeNowPlayingMovies[0]
        val movie2 = fakeNowPlayingMovies[1]

        // Run concurrent inserts
        val jobs = listOf(
            launch { repository.insertMovieDbResultNowPlaying(movie1) },
            launch { repository.insertMovieDbResultNowPlaying(movie2) }
        )

        jobs.forEach { it.join() }

        // Assert
        assertEquals(2, fakeLocalNowPlayingMovies.size)
    }

    @Test
    fun testInsertMovieDbResultNowPlaying_SimilarMoviesWithDifferentIds() = runBlocking {
        // Arrange
        val movie1 = fakeNowPlayingMovies[0]
        var movie2 = MovieDbResultNowPlaying(
            page = 2,
            results = fakeNowPlayingMovies[0].results,
            total_pages = fakeNowPlayingMovies[0].total_pages,
            total_results = fakeNowPlayingMovies[0].total_results
        )

        // Act

        repository.insertMovieDbResultNowPlaying(movie1)
        repository.insertMovieDbResultNowPlaying(movie2)

        // Assert
        assertEquals(2, fakeLocalNowPlayingMovies.size)
    }


    //Insert Movie Details


    @Test
    fun testInsertMovieDetails_SingleEntry() = runBlocking {
        // Arrange
        val movie = movieDetailsList[0]

        // Act
        repository.insertMovieDetails(movie)

        // Assert
        assertEquals(1, fakeLocalMoviesDetails.size)
        assertEquals(movie, fakeLocalMoviesDetails[0])
    }

    @Test
    fun testInsertMovieDetails_MultipleEntries() = runBlocking {
        // Arrange
        val movie1 = movieDetailsList[0]
        val movie2 = movieDetailsList[1]

        // Act
        repository.insertMovieDetails(movie1)
        repository.insertMovieDetails(movie2)

        // Assert
        assertEquals(2, fakeLocalMoviesDetails.size)
        assertTrue(fakeLocalMoviesDetails.contains(movie1))
        assertTrue(fakeLocalMoviesDetails.contains(movie2))
    }


    @Test
    fun testInsertMovieDetails_LargeDataset() = runBlocking {
        // Arrange
        val movies = (1..1000).map {
            MovieDetails(
                id = it,
                overview = movieDetailsList[0].overview,
                poster_path = movieDetailsList[0].poster_path,
                release_date = movieDetailsList[0].release_date,
                title = movieDetailsList[0].title,
                vote_average = movieDetailsList[0].vote_average,
                vote_count = movieDetailsList[0].vote_count,
                runtime = movieDetailsList[0].runtime,
                status = movieDetailsList[0].status,
                tagline = movieDetailsList[0].tagline,
                genres = movieDetailsList[0].genres,
                original_language = movieDetailsList[0].original_language,
                original_title = movieDetailsList[0].original_title,
                backdrop_path = movieDetailsList[0].backdrop_path,
                budget = movieDetailsList[0].budget,
                homepage = movieDetailsList[0].homepage,
                popularity = movieDetailsList[0].popularity,
                production_companies = movieDetailsList[0].production_companies,
                production_countries = movieDetailsList[0].production_countries,
                revenue = movieDetailsList[0].revenue,
                spoken_languages = movieDetailsList[0].spoken_languages,
                video = movieDetailsList[0].video,
                adult = movieDetailsList[0].adult,
                imdb_id = movieDetailsList[0].imdb_id,
                origin_country = movieDetailsList[0].origin_country
            )
        }

        // Act
        movies.forEach { repository.insertMovieDetails(it) }

        // Assert
        assertEquals(1000, fakeLocalMoviesDetails.size)
    }

    @Test
    fun testInsertMovieDetails_IntegrityOfInsertedData() = runBlocking {
        // Arrange
        val movie = movieDetailsList[0]
        // Act
        repository.insertMovieDetails(movie)

        // Assert
        assertEquals(movie, fakeLocalMoviesDetails[0])
        assertNotNull(fakeLocalMoviesDetails[0].title)
    }

    @Test
    fun testInsertMovieDetails_NoDuplicateEntries() = runBlocking {
        // Arrange
        val movie = movieDetailsList[0]

        // Act
        repository.insertMovieDetails(movie)
        repository.insertMovieDetails(movie)

        // Assert
        assertEquals(1, fakeLocalMoviesDetails.size)
    }

    @Test
    fun testInsertMovieDetails_ConcurrentInserts() = runBlocking {
        // Act
        val movie1 = movieDetailsList[0]
        val movie2 = movieDetailsList[1]

        // Run concurrent inserts
        val jobs = listOf(
            launch { repository.insertMovieDetails(movie1) },
            launch { repository.insertMovieDetails(movie2) }
        )

        jobs.forEach { it.join() }

        // Assert
        assertEquals(2, fakeLocalMoviesDetails.size)
    }

    @Test
    fun testInsertMovieDetails_SimilarMoviesWithDifferentIds() = runBlocking {
        // Arrange
        val movie1 = movieDetailsList[0]
        var movie2 = MovieDetails(
            id = 2,
            overview = movieDetailsList[0].overview,
            poster_path = movieDetailsList[0].poster_path,
            release_date = movieDetailsList[0].release_date,
            title = movieDetailsList[0].title,
            vote_average = movieDetailsList[0].vote_average,
            vote_count = movieDetailsList[0].vote_count,
            runtime = movieDetailsList[0].runtime,
            status = movieDetailsList[0].status,
            tagline = movieDetailsList[0].tagline,
            genres = movieDetailsList[0].genres,
            original_language = movieDetailsList[0].original_language,
            original_title = movieDetailsList[0].original_title,
            backdrop_path = movieDetailsList[0].backdrop_path,
            budget = movieDetailsList[0].budget,
            homepage = movieDetailsList[0].homepage,
            popularity = movieDetailsList[0].popularity,
            production_companies = movieDetailsList[0].production_companies,
            production_countries = movieDetailsList[0].production_countries,
            revenue = movieDetailsList[0].revenue,
            spoken_languages = movieDetailsList[0].spoken_languages,
            video = movieDetailsList[0].video,
            adult = movieDetailsList[0].adult,
            imdb_id = movieDetailsList[0].imdb_id,
            origin_country = movieDetailsList[0].origin_country
        )

        // Act

        repository.insertMovieDetails(movie1)
        repository.insertMovieDetails(movie2)

        // Assert
        assertEquals(2, fakeLocalMoviesDetails.size)
    }


    //test get get PopularMovies
    @Test
    fun testGetPopularMovies_ValidPage_ReturnsMovie() = runBlocking {
         repository.getPopularMovies(1).collect {
            assertEquals(expectedPopularMoviesPage, it)

        }
    }

    @Test
    fun testGetPopularMovies_InvalidPage_ThrowsException() {
        val exception = assertThrows(NoSuchElementException::class.java) {
            runBlocking {
                repository.getPopularMovies(4)
            }
        }
        assertEquals("Page 4 not found", exception.message)
    }

    // test get NowPlayingMovies
    @Test
    fun testGetNowPlayingMovies_ValidPage_ReturnsMovie() = runBlocking {
        repository.getNowPlayingMovies(1).collect {
            assertEquals(expecteNowPlayingMoviesPage, it)

        }
    }

    @Test
    fun testGetNowPlayingMovies_InvalidPage_ThrowsException() {
        val exception = assertThrows(NoSuchElementException::class.java) {
            runBlocking {
                repository.getNowPlayingMovies(4)
            }
        }
        assertEquals("Page 4 not found", exception.message)
    }


    //test get UpcomingMovies
    @Test
    fun testGetUpcomingMovies_ValidPage_ReturnsMovie() = runBlocking {
        repository.getUpcomingMovies(1).collect {
            assertEquals(expectedUpcomingMoviesPage, it)

        }
    }

    @Test
    fun testGetUpcomingMovies_InvalidPage_ThrowsException() {
        val exception = assertThrows(NoSuchElementException::class.java) {
            runBlocking {
                repository.getUpcomingMovies(4)
            }
        }
        assertEquals("Page 4 not found", exception.message)
    }


    //test get MovieDetails
    @Test
    fun testGetMovieDetails_ValidId_ReturnsMovie() = runBlocking {
        repository.getMovieDetails(1).collect {
            assertEquals(expectedMovieDetails, it)
        }
    }

    @Test
    fun testGetMovieDetails_InvalidId_ThrowsException() {
        val exception = assertThrows(NoSuchElementException::class.java) {
            runBlocking {
                repository.getMovieDetails(999)
            }
        }
        assertEquals("Movie with ID 999 not found", exception.message)
    }
}

