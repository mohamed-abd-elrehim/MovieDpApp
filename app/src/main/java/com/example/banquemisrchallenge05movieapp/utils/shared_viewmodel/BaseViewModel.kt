package com.example.banquemisrchallenge05movieapp.utils.shared_viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
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


    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        val errorMessage = when (exception) {
            is IOException -> R.string.network_error
            is HttpException -> R.string.server_error
            is TimeoutException -> R.string.timeout_error
            else -> R.string.unexpected_error
        }
        _isRefreshing.value = false
    }

    protected fun <T> handleFetchList(
        fetch: suspend () -> Flow<T>,
        stateFlow: MutableStateFlow<ApiState<T>>
    ) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                fetch().collect { result ->
                    stateFlow.value = ApiState.Success(result)
                }
            } catch (e: Exception) {
                stateFlow.value = ApiState.Error(R.string.unexpected_error)
            }
        }
    }
}
