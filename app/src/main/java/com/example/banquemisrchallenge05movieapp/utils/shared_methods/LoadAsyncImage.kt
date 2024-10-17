package com.example.banquemisrchallenge05movieapp.utils.shared_methods

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage

import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy


@Composable
fun LoadAsyncImage(
    context: Context,
    imageUrl: String,
    imageTitle: String,
    modifier: Modifier = Modifier
) {
    // Load image using Coil's AsyncImage composable
    AsyncImage(
        imageLoader =cachedImageLoader (context),
        model = imageUrl,
        contentDescription = imageTitle,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(250.dp)
            .fillMaxSize()
    )
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
