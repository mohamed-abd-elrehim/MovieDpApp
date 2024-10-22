package com.example.banquemisrchallenge05movieapp.listscreen.viewModel

import com.example.banquemisrchallenge05movieapp.utils.data_layer.FakeRepositoryImpl
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakeNowPlayingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakePopularMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.fakeUpcomingMovies
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.movieDetailsList
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Response.*
import java.io.IOException
import java.util.concurrent.TimeoutException

class ListScreenViewModelTest {

    private lateinit var fakeRepository: FakeRepositoryImpl
    private lateinit var viewModel: ListScreenViewModel

    val expectedPopularMoviesPage = fakePopularMovies[1]
    val expectedUpcomingMoviesPage = fakeUpcomingMovies[1]
    val expecteNowPlayingMoviesPage = fakeNowPlayingMovies[1]

    @Before
    fun setUp() {
        fakeRepository = FakeRepositoryImpl()
        viewModel = ListScreenViewModel(fakeRepository)
    }

    @Test
    fun `fetchNowPlayingMovies should emit Loading and Success states`() = runTest {
        viewModel.fetchNowPlayingList(2)

        // Check Loading state
        assertEquals(ApiState.Loading, viewModel.nowPlayingList.value)

        // Wait for Success state
        val result = viewModel.nowPlayingList.first { it is ApiState.Success }
        assertTrue(result is ApiState.Success)

        // Check the data is as expected
        val movie = (result as ApiState.Success).data
        assertEquals(expecteNowPlayingMoviesPage.page, movie.page)
        assertEquals(expecteNowPlayingMoviesPage.total_pages, movie.total_pages)
    }

    @Test
    fun `fetchUpcomingMovies should emit Loading and Success states`() = runTest {
        viewModel.fetchUpcomingList(2)

        // Check Loading state
        assertEquals(ApiState.Loading, viewModel.upcomingList.value)

        // Wait for Success state
        val result = viewModel.upcomingList.first { it is ApiState.Success }
        assertTrue(result is ApiState.Success)

        // Check the data is as expected
        val movie = (result as ApiState.Success).data
        assertEquals(expectedUpcomingMoviesPage.page, movie.page)
        assertEquals(expectedUpcomingMoviesPage.total_pages, movie.total_pages)
    }

    @Test
    fun `fetchPopularMovies should emit Loading and Success states`() = runTest {
        viewModel.fetchPopularList(2)

        // Check Loading state
        assertEquals(ApiState.Loading, viewModel.popularList.value)

        // Wait for Success state
        val result = viewModel.popularList.first { it is ApiState.Success }
        assertTrue(result is ApiState.Success)

        // Check the data is as expected
        val movie = (result as ApiState.Success).data
        assertEquals(expectedPopularMoviesPage.page, movie.page)
        assertEquals(expectedPopularMoviesPage.total_pages, movie.total_pages)
    }

    @Test
    fun `fetchPopularMovies should emit Error state when movie page is not found`() = runTest {
        viewModel.fetchPopularList(999)
        val result = viewModel.popularList.first { it is ApiState.Error }
        assertTrue(result is ApiState.Error)
        val errorMessage = (result as ApiState.Error).message
        assertEquals(R.string.unexpected_error, errorMessage)
    }

    @Test
    fun `fetchNowPlayingMovies should emit Error state when movie page is not found`() = runTest {
        viewModel.fetchNowPlayingList(999)
        val result = viewModel.nowPlayingList.first { it is ApiState.Error }
        assertTrue(result is ApiState.Error)
        val errorMessage = (result as ApiState.Error).message
        assertEquals(R.string.unexpected_error, errorMessage)
    }

    @Test
    fun `fetchUpcomingMovies should emit Error state when movie page is not found`() = runTest {
        viewModel.fetchUpcomingList(999)
        val result = viewModel.upcomingList.first { it is ApiState.Error }
        assertTrue(result is ApiState.Error)
        val errorMessage = (result as ApiState.Error).message
        assertEquals(R.string.unexpected_error, errorMessage)
    }


    @Test
    fun `insertMovieDbResultPopular should add MovieDbResultPopular to repository`() = runTest {
        val movie = expectedPopularMoviesPage
        viewModel.insertMovieDbResultPopular(movie)
        val insertedMovie = fakeRepository.getPopularMovies(expectedPopularMoviesPage.page).first()
        assertEquals(movie, insertedMovie)
    }

    @Test
    fun `insertMovieDbResultUpcoming should add MovieDbResultUpcoming to repository`() = runTest {
        val movie = expectedUpcomingMoviesPage
        viewModel.insertMovieDbResultUpcoming(movie)
        val insertedMovie = fakeRepository.getUpcomingMovies(expectedPopularMoviesPage.page).first()
        assertEquals(movie, insertedMovie)
    }

    @Test
    fun `insertMovieDbResultNowPlaying should add MovieDbResultNowPlaying to repository`() =
        runTest {
            val movie = expecteNowPlayingMoviesPage
            viewModel.insertMovieDbResultNowPlaying(movie)
            val insertedMovie =
                fakeRepository.getNowPlayingMovies(expecteNowPlayingMoviesPage.page).first()
            assertEquals(movie, insertedMovie)
        }


    @Test
    fun `getPopularMovies should return existing local movie details`() = runTest {
        val movie = expectedPopularMoviesPage
        fakeRepository.insertMovieDbResultPopular(movie)
        viewModel.getPopularMovies(movie.page)
        val result = viewModel.localPopularList.first()
        assertNotNull(result)
        assertEquals(movie, result)
    }


    @Test
    fun `getUpcomingMovies should return existing local movie details`() = runTest {
        val movie = expectedUpcomingMoviesPage
        fakeRepository.insertMovieDbResultUpcoming(movie)
        viewModel.getUpcomingMovies(movie.page)
        val result = viewModel.localUpcomingList.first()
        assertNotNull(result)
        assertEquals(movie, result)
    }


    @Test
    fun `getNowPlayingMovies should return existing local movie details`() = runTest {
        val movie = expecteNowPlayingMoviesPage
        fakeRepository.insertMovieDbResultNowPlaying(movie)
        viewModel.getNowPlayingMovies(movie.page)
        val result = viewModel.localNowPlayingList.first()
        assertNotNull(result)
        assertEquals(movie, result)
    }


    @Test
    fun `getNowPlayingMovies should not update localNowPlayingList for non-existing movie`() =
        runTest {
            viewModel.getNowPlayingMovies(999)
            val result = viewModel.localNowPlayingList.first()
            assertNull(result)
        }

    @Test
    fun `getUpcomingMovies should not update localUpcomingList for non-existing movie`() =
        runTest {
            viewModel.getUpcomingMovies(999)
            val result = viewModel.localUpcomingList.first()
            assertNull(result)
        }

    @Test
    fun `getPopularMovies should not update localPopularList for non-existing movie`() = runTest {
        viewModel.getPopularMovies(999)
        val result = viewModel.localPopularList.first()
        assertNull(result)
    }


    // Test isRefreshing
    @Test
    fun `fetchUpcomingMovies should set isRefreshing to false after fetching`() = runTest {
        viewModel.fetchUpcomingList(1)
        assertFalse(viewModel.isRefreshing.first())
    }

    @Test
    fun `fetchPopularMovies should set isRefreshing to false after fetching`() = runTest {
        viewModel.fetchPopularList(1)
        assertFalse(viewModel.isRefreshing.first())
    }

    @Test
    fun `fetchNowPlayingMovies should set isRefreshing to false after fetching`() = runTest {
        viewModel.fetchNowPlayingList(1)
        assertFalse(viewModel.isRefreshing.first())
    }


    @Test
    fun `test fetchNowPlayingMovies IOException error handling`() = runTest {
        // Create a MutableStateFlow to simulate the API state
        val nowPlayingListState =
            MutableStateFlow<ApiState<MovieDbResultNowPlaying>>(ApiState.Loading)

        // Create a CoroutineExceptionHandler using the nowPlayingListState
        val exceptionHandler = viewModel.createExceptionHandler(nowPlayingListState)

        // Simulate throwing an IOException
        val exception = IOException()
        exceptionHandler.handleException(coroutineContext, exception)

        // Assert that the state has changed to Error
        assertTrue(nowPlayingListState.value is ApiState.Error)
        assertEquals(R.string.network_error, (nowPlayingListState.value as ApiState.Error).message)
    }

    @Test
    fun `test fetchPopularMovies IOException error handling`() = runTest {
        val popularListState = MutableStateFlow<ApiState<MovieDbResultPopular>>(ApiState.Loading)

        val exceptionHandler = viewModel.createExceptionHandler(popularListState)

        val exception = IOException()
        exceptionHandler.handleException(coroutineContext, exception)

        assertTrue(popularListState.value is ApiState.Error)
        assertEquals(R.string.network_error, (popularListState.value as ApiState.Error).message)
    }

    @Test
    fun `test fetchUpcomingMovies IOException error handling`() = runTest {
        val upcomingListState = MutableStateFlow<ApiState<MovieDbResultUpcoming>>(ApiState.Loading)

        val exceptionHandler = viewModel.createExceptionHandler(upcomingListState)

        val exception = IOException()
        exceptionHandler.handleException(coroutineContext, exception)

        assertTrue(upcomingListState.value is ApiState.Error)
        assertEquals(R.string.network_error, (upcomingListState.value as ApiState.Error).message)
    }

    @Test
    fun `test fetchNowPlayingMovies TimeoutException error handling`() = runTest {
        val nowPlayingState = MutableStateFlow<ApiState<MovieDbResultNowPlaying>>(ApiState.Loading)
        val exceptionHandler = viewModel.createExceptionHandler(nowPlayingState)

        val exception = TimeoutException()
        exceptionHandler.handleException(coroutineContext, exception)

        assertTrue(nowPlayingState.value is ApiState.Error)
        assertEquals(R.string.timeout_error, (nowPlayingState.value as ApiState.Error).message)
    }

    @Test
    fun `test fetchPopularMovies TimeoutException error handling`() = runTest {
        val popularListState = MutableStateFlow<ApiState<MovieDbResultPopular>>(ApiState.Loading)
        val exceptionHandler = viewModel.createExceptionHandler(popularListState)

        val exception = TimeoutException()
        exceptionHandler.handleException(coroutineContext, exception)

        assertTrue(popularListState.value is ApiState.Error)
        assertEquals(R.string.timeout_error, (popularListState.value as ApiState.Error).message)
    }

    @Test
    fun `test fetchUpcomingMovies TimeoutException error handling`() = runTest {
        val upcomingListState = MutableStateFlow<ApiState<MovieDbResultUpcoming>>(ApiState.Loading)
        val exceptionHandler = viewModel.createExceptionHandler(upcomingListState)

        val exception = TimeoutException()
        exceptionHandler.handleException(coroutineContext, exception)

        assertTrue(upcomingListState.value is ApiState.Error)
        assertEquals(R.string.timeout_error, (upcomingListState.value as ApiState.Error).message)
    }

    @Test
    fun `test fetchNowPlayingMovies unexpected exception handling`() = runTest {
        val nowPlayingState = MutableStateFlow<ApiState<MovieDbResultNowPlaying>>(ApiState.Loading)
        val exceptionHandler = viewModel.createExceptionHandler(nowPlayingState)

        val exception = Exception("Unexpected Error")
        exceptionHandler.handleException(coroutineContext, exception)

        assertTrue(nowPlayingState.value is ApiState.Error)
        assertEquals(R.string.unexpected_error, (nowPlayingState.value as ApiState.Error).message)
    }

    @Test
    fun `test fetchPopularMovies unexpected exception handling`() = runTest {
        val popularListState = MutableStateFlow<ApiState<MovieDbResultPopular>>(ApiState.Loading)
        val exceptionHandler = viewModel.createExceptionHandler(popularListState)

        val exception = Exception("Unexpected Error")
        exceptionHandler.handleException(coroutineContext, exception)

        assertTrue(popularListState.value is ApiState.Error)
        assertEquals(R.string.unexpected_error, (popularListState.value as ApiState.Error).message)
    }

    @Test
    fun `test fetchUpcomingMovies unexpected exception handling`() = runTest {
        val upcomingListState = MutableStateFlow<ApiState<MovieDbResultUpcoming>>(ApiState.Loading)
        val exceptionHandler = viewModel.createExceptionHandler(upcomingListState)

        val exception = Exception("Unexpected Error")
        exceptionHandler.handleException(coroutineContext, exception)

        assertTrue(upcomingListState.value is ApiState.Error)
        assertEquals(R.string.unexpected_error, (upcomingListState.value as ApiState.Error).message)
    }

    @Test
    fun `test fetchNowPlayingMovies server error handling`() = runBlocking {
        // Simulating a server error response
        val httpException = HttpException(error<Any>(500, ResponseBody.create(null, "Server Error")))

        // Create a MutableStateFlow for the nowPlayingList
        val nowPlayingState = MutableStateFlow<ApiState<MovieDbResultNowPlaying>>(ApiState.Loading)

        // Create the exception handler for the nowPlayingState
        val exceptionHandler = viewModel.createExceptionHandler(nowPlayingState)

        // Handle the server error exception
        exceptionHandler.handleException(CoroutineName("test"), httpException)

        // Assertions
        assertTrue(nowPlayingState.value is ApiState.Error)
        assertEquals(R.string.server_error, (nowPlayingState.value as ApiState.Error).message)
        assertEquals(false, viewModel.isRefreshing.value)
    }

    @Test
    fun `test fetchPopularMovies server error handling`() = runBlocking {
        // Simulating a server error response
        val httpException = HttpException(error<Any>(500, ResponseBody.create(null, "Server Error")))

        // Create a MutableStateFlow for the popularList
        val popularState = MutableStateFlow<ApiState<MovieDbResultPopular>>(ApiState.Loading)

        // Create the exception handler for the popularState
        val exceptionHandler = viewModel.createExceptionHandler(popularState)

        // Handle the server error exception
        exceptionHandler.handleException(CoroutineName("test"), httpException)

        // Assertions
        assertTrue(popularState.value is ApiState.Error)
        assertEquals(R.string.server_error, (popularState.value as ApiState.Error).message)
        assertEquals(false, viewModel.isRefreshing.value)
    }

    @Test
    fun `test fetchUpcomingMovies server error handling`() = runBlocking {
        // Simulating a server error response
        val httpException = HttpException(error<Any>(500, ResponseBody.create(null, "Server Error")))

        // Create a MutableStateFlow for the upcomingList
        val upcomingState = MutableStateFlow<ApiState<MovieDbResultUpcoming>>(ApiState.Loading)

        // Create the exception handler for the upcomingState
        val exceptionHandler = viewModel.createExceptionHandler(upcomingState)

        // Handle the server error exception
        exceptionHandler.handleException(CoroutineName("test"), httpException)

        // Assertions
        assertTrue(upcomingState.value is ApiState.Error)
        assertEquals(R.string.server_error, (upcomingState.value as ApiState.Error).message)
        assertEquals(false, viewModel.isRefreshing.value)
    }



}


