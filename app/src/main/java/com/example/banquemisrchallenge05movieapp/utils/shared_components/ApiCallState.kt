package com.example.banquemisrchallenge05movieapp.utils.shared_components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState


@Composable
fun <T> ApiCallState(navController: NavController, apiState: ApiState<T> , onSuccess: @Composable (T) -> Unit) {
    val context=LocalContext.current
    val TAG="ApiCallState"

    when (apiState) {
        ApiState.Loading -> {
            MovieAppLoadingIndicator()
        }

        is ApiState.Success -> {

            onSuccess(apiState.data)

        }

        is ApiState.Error -> {
            Toast.makeText(context, apiState.message, Toast.LENGTH_SHORT).show()
        }
    }
}