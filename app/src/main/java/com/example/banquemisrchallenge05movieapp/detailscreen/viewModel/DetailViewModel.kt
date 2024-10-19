package com.example.banquemisrchallenge05movieapp.detailscreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.utils.data_layer.Repository
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val _movieDetails = MutableStateFlow<ApiState<MovieDetails>>(ApiState.Loading)
    val movieDetails = _movieDetails.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        val errorMessage = when (exception) {
            is IOException -> R.string.network_error
            is HttpException -> R.string.server_error
            is TimeoutException -> R.string.timeout_error
            else -> R.string.unexpected_error
        }
        _movieDetails.value = ApiState.Error(errorMessage)
        _isRefreshing.value = false
    }


    fun featchMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _movieDetails.value = ApiState.Loading


            try {
                repository.fetchMovieDetails(movieId).collect { result ->
                    _movieDetails.value = ApiState.Success(result)

                }
            } catch (e: Exception) {
                _movieDetails.value = ApiState.Error(R.string.unexpected_error)
            }
        }


    }


}

