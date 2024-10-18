package com.example.banquemisrchallenge05movieapp.listscreen.components

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.banquemisrchallenge05movieapp.utils.shared_components.MovieAppLoadingIndicator
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState


@Composable
fun <T> ApiCallState(title: String, apiState: ApiState<T>,currentPage:Int,totalPages:Int,onPageChange: (Int) -> Unit) {
    val context=LocalContext.current
    val TAG="ApiCallState"

    when (apiState) {
        ApiState.Loading -> {
            MovieAppLoadingIndicator()
        }

        is ApiState.Success -> {
            Log.d(TAG, "Success $title")
            ListContent( apiState.data,currentPage,totalPages , onPageChange ={
                onPageChange(it)


            })
        }

        is ApiState.Error -> {
            Toast.makeText(context, apiState.message, Toast.LENGTH_SHORT).show()
        }
    }
}