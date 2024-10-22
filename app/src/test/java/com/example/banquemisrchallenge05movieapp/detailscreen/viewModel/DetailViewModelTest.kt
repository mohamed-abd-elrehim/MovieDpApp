package com.example.banquemisrchallenge05movieapp.detailscreen.viewModel

import com.example.banquemisrchallenge05movieapp.utils.data_layer.FakeRepositoryImpl
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.movieDetailsList
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import  com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import okhttp3.ResponseBody
import org.junit.Test
import java.io.IOException
import java.util.concurrent.TimeoutException

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
class DetailViewModelTest
{
    private lateinit var fakeRepository: FakeRepositoryImpl
    private lateinit var viewModel: DetailViewModel
    val expectedMovieDetails = movieDetailsList[0]

    @Before
    fun setUp() {
        fakeRepository = FakeRepositoryImpl()
        viewModel = DetailViewModel(fakeRepository)
    }


    @Test
    fun `fetchMovieDetails should emit Loading and Success states`() = runTest {
        viewModel.featchMovieDetails(1)

        // Check Loading state
        assertEquals(ApiState.Loading, viewModel.movieDetails.value)

        // Wait for Success state
        val result = viewModel.movieDetails.first { it is ApiState.Success }
        assertTrue(result is ApiState.Success)

        // Check the data is as expected
        val movieDetails = (result as ApiState.Success).data
        assertEquals(expectedMovieDetails.id, movieDetails.id)
        assertEquals(expectedMovieDetails.title, movieDetails.title)
    }

    @Test
    fun `fetchMovieDetails should emit Error state when movie is not found`() = runTest {
        viewModel.featchMovieDetails(999)
        val result = viewModel.movieDetails.first { it is ApiState.Error }
        assertTrue(result is ApiState.Error)
        val errorMessage = (result as ApiState.Error).message
        assertEquals(R.string.unexpected_error, errorMessage)
    }

    // Test insertMovieDetails
    @Test
    fun `insertMovieDetails should add movie details to repository`() = runTest {
        val movie = expectedMovieDetails
        viewModel.insertMovieDetails(movie)
        val insertedMovie = fakeRepository.getMovieDetails(expectedMovieDetails.id).first()
        assertEquals(movie, insertedMovie)
    }


    // Test getMovieDetails
    @Test
    fun `getMovieDetails should return existing local movie details`() = runTest {
        val movie = movieDetailsList[1]
        fakeRepository.insertMovieDetails(movie)
        viewModel.getMovieDetails(movieDetailsList[1].id)
        val result = viewModel.localMovieDetails.first()
        assertNotNull(result)
        assertEquals(movie, result)
    }
    @Test
    fun `getMovieDetails should not update localMovieDetails for non-existing movie`() = runTest {
        viewModel.getMovieDetails(999)
        val result = viewModel.localMovieDetails.first()
        assertNull(result)
    }

    // Test isRefreshing
    @Test
    fun `fetchMovieDetails should set isRefreshing to false after fetching`() = runTest {
        viewModel.featchMovieDetails(1)
        assertFalse(viewModel.isRefreshing.first())
    }

    @Test
    fun `test IOException error handling`() = runTest {
        val exceptionHandler = viewModel.exceptionHandler

        val exception = IOException()
        exceptionHandler.handleException(coroutineContext, exception)

        assertEquals(R.string.network_error, (viewModel.movieDetails.value as ApiState.Error).message)
    }



    @Test
    fun `test TimeoutException error handling`() = runTest {
        val exceptionHandler = viewModel.exceptionHandler

        val exception = TimeoutException()
        exceptionHandler.handleException(coroutineContext, exception)

        assertEquals(R.string.timeout_error, (viewModel.movieDetails.value as ApiState.Error).message)
    }

    @Test
    fun `test unexpected exception handling`() = runTest {
        val exceptionHandler = viewModel.exceptionHandler

        val exception = Exception("Unexpected Error")
        exceptionHandler.handleException(coroutineContext, exception)

        assertEquals(R.string.unexpected_error, (viewModel.movieDetails.value as ApiState.Error).message)
    }




    @Test
    fun testservererrorhandling() = runBlocking {
        val httpException = HttpException(
            Response.error<Any>(500,
                "Server Error".toResponseBody(null)
            ))

        viewModel.exceptionHandler.handleException(CoroutineName("test"), httpException)

        assertEquals(ApiState.Error(R.string.server_error), viewModel.movieDetails.value )
        assertEquals(false, viewModel.isRefreshing.value)
    }

}