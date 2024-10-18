package com.example.banquemisrchallenge05movieapp.utils.shared_models

import com.example.banquemisrchallenge05movieapp.R


sealed class ApiState<out T> {
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val message: Int) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()
}

