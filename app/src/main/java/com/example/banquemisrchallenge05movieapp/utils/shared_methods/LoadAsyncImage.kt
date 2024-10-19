package com.example.banquemisrchallenge05movieapp.utils.shared_methods

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage

import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.utils.shared_components.MovieAppLoadingIndicator


@Composable
fun LoadAsyncImage(
    context: Context,
    imageUrl: String,
    imageTitle: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    // Load image using Coil's AsyncImage composable
    AsyncImage(
        //imageLoader =cachedImageLoader (context),
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .listener(
                onStart = {
                    isLoading = true
                    isError = false
                },
                onSuccess = { _, _ ->
                    isLoading = false
                    isError = false
                },
                onError = { _, _ ->
                    isLoading = false
                    isError = true
                }
            )
            .build(),
        contentDescription = imageTitle,
        modifier = modifier,
        contentScale = contentScale

    )
    if (isError) {
        AsyncImage(
            model = R.drawable.noimage,
            contentDescription = "Error Image",
            modifier = modifier,
            contentScale = contentScale
        )
    }
    if (isLoading) {
        MovieAppLoadingIndicator(modifier = Modifier.size(200.dp, 200.dp))
    }
}

fun cachedImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        // Enable memory caching
        .memoryCachePolicy(CachePolicy.ENABLED) // Enable in-memory cache
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25) // Use 25% of available memory for caching
                .build()
        }
        // Enable disk caching
        .diskCachePolicy(CachePolicy.ENABLED) // Enable disk cache
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache")) // Set cache directory
                .maxSizePercent(0.02) // Use 2% of available disk space for caching
                .build()
        }
        .build() // Build the ImageLoader
}

// Clear the cache
fun clearCache(context: Context) {
    val imageLoader = cachedImageLoader(context)
    imageLoader.diskCache?.clear()
    imageLoader.memoryCache?.clear()
}
