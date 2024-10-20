package com.example.banquemisrchallenge05movieapp.utils.data_layer

import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.FakeLocalDataSourceImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.LocalDataSource
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.FakeRemoteDataSourceImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.RemoteDataSource
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakeNowPlayingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakePopularMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakeUpcomingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.movieDetailsList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.io.IOException


class RepositoryImplTest
{

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
    fun fetchUpcomingMovies_ReturnsExpectedData()= runTest{
        val result = repository.fetchUpcomingMovies(page = 1).collect{
            assertEquals(expectedUpcomingMoviesPage, it)
        }
    }


    @Test
    fun fetchUpcomingMovies_ReturnsEmptyList_WhenNoMoviesAvailable()= runTest{
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
    fun fetchUpcomingMovies_ThrowsException_WhenRemoteSourceFails()= runTest{
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
    fun fetchUpcomingMovies_HandlesNullResponseGracefully()= runTest{
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
        val result = repository.fetchNowPlayingMovies( page = 1).collect {
            assertEquals(expecteNowPlayingMoviesPage, it)
        }
    }

    @Test
    fun fetchNowPlayingMovies_ReturnsEmptyList_WhenNoMoviesAvailable() = runTest {
        remoteDataSource.emptyMovies = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.fetchNowPlayingMovies( page = 1).collect {
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
                repository.fetchNowPlayingMovies( page = 1).collect {
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
                repository.fetchNowPlayingMovies( page = page).collect {
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



}

