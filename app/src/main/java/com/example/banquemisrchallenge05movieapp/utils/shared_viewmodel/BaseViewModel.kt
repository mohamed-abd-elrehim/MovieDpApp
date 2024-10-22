package com.example.banquemisrchallenge05movieapp.utils.shared_viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException


abstract class BaseViewModel : ViewModel() {
    protected val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()


     fun <T> createExceptionHandler(stateFlow: MutableStateFlow<ApiState<T>>): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            val errorMessage = when (exception) {
                is IOException -> R.string.network_error
                is HttpException -> R.string.server_error
                is TimeoutException -> R.string.timeout_error
                else -> R.string.unexpected_error
            }
            stateFlow.value = ApiState.Error(errorMessage)
            _isRefreshing.value = false
        }
    }

    protected fun <T> handleFetchList(
        fetch: suspend () -> Flow<T>,
        stateFlow: MutableStateFlow<ApiState<T>>,
        totalPage: MutableStateFlow<Int>? = null,


    ) {
        val exceptionHandler = createExceptionHandler(stateFlow)

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                fetch().collect { result ->
                    stateFlow.value = ApiState.Success(result)

                    when (result) {
                        is MovieDbResultNowPlaying -> {
                            totalPage?.value = result.total_pages
                        }
                        is MovieDbResultPopular -> {
                            totalPage?.value = result.total_pages
                        }
                        is MovieDbResultUpcoming -> {
                            totalPage?.value = result.total_pages
                        }
                        else -> {
                            totalPage?.value = 0
                        }

                    }

                }
            } catch (e: Exception) {
                stateFlow.value = ApiState.Error(R.string.unexpected_error)
            }
        }
    }
}
