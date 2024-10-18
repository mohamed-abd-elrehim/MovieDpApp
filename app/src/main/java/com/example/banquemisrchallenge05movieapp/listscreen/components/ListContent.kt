package com.example.banquemisrchallenge05movieapp.listscreen.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming

@Composable
fun <T> ListContent(list: T) {
    val TAG = "ListContent"
    val context = LocalContext.current
    val result = when (list) {
        is MovieDbResultNowPlaying -> list.results
        is MovieDbResultPopular -> list.results
        is MovieDbResultUpcoming -> list.results
        else -> emptyList()
    }
    Log.d(TAG, "ListContent: $result")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Sets 2 columns
        modifier = Modifier.fillMaxWidth(), // Makes the grid fill the width
        content = {
            items(result) { item ->
                ListScreenElevationCard(
                    movieResult = item,
                    context = context
                )
            }
        }
    )
}

