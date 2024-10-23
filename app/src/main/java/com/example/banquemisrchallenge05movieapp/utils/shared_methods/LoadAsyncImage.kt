package com.example.banquemisrchallenge05movieapp.utils.shared_methods

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.compose.AsyncImage
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.utils.shared_components.MovieAppLoadingIndicator

// LoadState sealed class for managing image loading states
sealed class LoadState {
    object Loading : LoadState()
    object Success : LoadState()
    object Error : LoadState()
}

// Singleton object for managing ImageLoader
object ImageLoaderProvider {
    // Lazy initialization of the ImageLoader instance
    private var imageLoader: ImageLoader? = null

    // Function to provide the ImageLoader instance
    fun getImageLoader(context: Context): ImageLoader {
        if (imageLoader == null) {
            imageLoader = ImageLoader.Builder(context)
                .memoryCachePolicy(CachePolicy.ENABLED) // Enable in-memory cache
                .memoryCache {
                    MemoryCache.Builder(context)
                        .maxSizePercent(0.25) // Use 25% of available memory for caching
                        .build()
                }
                .diskCachePolicy(CachePolicy.ENABLED) // Enable disk cache
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("image_cache")) // Set cache directory
                        .maxSizePercent(0.02) // Use 2% of available disk space for caching
                        .build()
                }
                .build()
        }
        return imageLoader!!
    }
}

// Function to clear cache
fun clearCache(context: Context) {
    val imageLoader = ImageLoaderProvider.getImageLoader(context)
    imageLoader.diskCache?.clear()
    imageLoader.memoryCache?.clear()
}
@Composable
fun LoadAsyncImage(
    context: Context,
    imageUrl: String,
    imageTitle: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop // Default value for contentScale
) {
    var loadState by remember { mutableStateOf<LoadState>(LoadState.Loading) }
    var timeoutReached by remember { mutableStateOf(false) }

    // LaunchedEffect to manage the timeout
    LaunchedEffect(loadState) {
        if (loadState == LoadState.Loading) {
            kotlinx.coroutines.delay(5000)
            if (loadState == LoadState.Loading) {
                timeoutReached = true
            }
        }
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .error(R.drawable.noimage)
            .data(imageUrl)
            .crossfade(true)
            .listener(
                onStart = {
                    loadState = LoadState.Loading
                },
                onSuccess = { _, _ ->
                    loadState = LoadState.Success
                },
                onError = { _, _ ->
                    loadState = LoadState.Error
                }
            )
            .build(),
        contentDescription = imageTitle,
        modifier = modifier,
        contentScale = contentScale
    )

    // Handling different load states
    when {
        loadState == LoadState.Loading && !timeoutReached -> {
            MovieAppLoadingIndicator(modifier = Modifier.size(200.dp))
        }
        loadState == LoadState.Error || timeoutReached -> {
            AsyncImage(
                model = R.drawable.noimage,
                contentDescription = "Error Image",
                modifier = modifier,
                contentScale = contentScale
            )
        }
        loadState == LoadState.Success -> {
        }
    }
}
