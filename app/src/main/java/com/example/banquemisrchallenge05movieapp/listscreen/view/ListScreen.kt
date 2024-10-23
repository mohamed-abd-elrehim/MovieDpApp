package com.example.banquemisrchallenge05movieapp.listscreen.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.ui.theme.PrimaryColor
import com.example.banquemisrchallenge05movieapp.utils.constants.NavigationKeys
import com.example.banquemisrchallenge05movieapp.utils.shared_components.ApiCallState
import com.example.banquemisrchallenge05movieapp.listscreen.components.ListContent
import com.example.banquemisrchallenge05movieapp.listscreen.components.Pagination
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.utils.shared_components.Gap
import com.example.banquemisrchallenge05movieapp.utils.shared_components.LottieWithText
import com.example.banquemisrchallenge05movieapp.utils.shared_components.displayNoInternetMessage
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.isNetworkAvailable

@Composable
fun ListScreen(navController: NavHostController, viewModel: ListScreenViewModel) {
    val context = LocalContext.current
    val nowPlayingList by viewModel.nowPlayingList.collectAsStateWithLifecycle()
    val popularList by viewModel.popularList.collectAsStateWithLifecycle()
    val upcomingList by viewModel.upcomingList.collectAsStateWithLifecycle()

    val localPopularMovies by viewModel.localPopularList.collectAsStateWithLifecycle()
    val localNowPlayingMovies by viewModel.localNowPlayingList.collectAsStateWithLifecycle()
    val localUpcomingMovies by viewModel.localUpcomingList.collectAsStateWithLifecycle()

    val selectedTabIndex by viewModel.selectedTabIndex.collectAsStateWithLifecycle()
    val tabs = listOf(NavigationKeys.NowPlaying, NavigationKeys.Popular, NavigationKeys.Upcoming)
    val totalPages by viewModel.totalPages.collectAsStateWithLifecycle()
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val isNetworkAvailable = isNetworkAvailable(context)

    var goToNextPage by remember { mutableStateOf(false) }



    var isLocalDataAvailable by remember { mutableStateOf(false) }


    // Fetch local movies initially
    viewModel.getLocalMoviesIfAvailable(selectedTabIndex, currentPage)

    // Trigger remote fetch if local data is not available
    if (!isLocalDataAvailable && isNetworkAvailable) {
        LaunchedEffect(currentPage) {
            viewModel.fetchRemoteMoviesIfLocalIsEmpty(selectedTabIndex, currentPage)
        }
    }

    Column(
        modifier = Modifier

            .background(brush = Brush.verticalGradient(
            colors = listOf( Color.White,Color.White,PrimaryColor),
            startY = 100f))
            .fillMaxSize()
    ) {

        Gap(height = 30)
        TabRow(selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(MaterialTheme.shapes.medium),
            indicator = {},
            containerColor = Color.Transparent,
            divider = {}) {
            tabs.forEachIndexed { index, title ->
                val scale by animateFloatAsState(
                    if (selectedTabIndex == index) 1.1f else 1f
                )

                Tab(
                    text = {
                        Text(
                            title,
                            color = if (selectedTabIndex == index) Color.White else Color.Black
                        )
                    },
                    onClick = { viewModel.setSelectedTabIndex(index)
                              viewModel.setCurrentPage(1)
                              },
                    selected = selectedTabIndex == index,
                    modifier = Modifier
                        .padding(horizontal = 7.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                        .border(1.dp, Color.Black, shape = MaterialTheme.shapes.medium)
                        .clip(MaterialTheme.shapes.medium)
                        .background(if (selectedTabIndex == index) PrimaryColor else Color.White)
                )
            }
        }

        Gap(8)

        // Pagination Component
        Pagination(
            currentPage = currentPage,
            totalPages = totalPages,
            onPageChange = { newPage ->
                goToNextPage = true
                viewModel.setCurrentPage(newPage)

            }
        )
        val isNetworkAvailable = isNetworkAvailable(context)

        Crossfade(targetState = selectedTabIndex) { currentTab ->
            when (currentTab) {
                0 -> handleMovieContent(
                    navController,
                    localNowPlayingMovies,
                    nowPlayingList,
                    goToNextPage,
                    isNetworkAvailable(context),
                    onLocalDataAvailable = { isLocalDataAvailable = it },
                ) {
                    ListContent(navController, it,goToNextPage)
                    goToNextPage = false
                    viewModel.insertMovieDbResultNowPlaying(it)
                }

                1 -> handleMovieContent(
                    navController,
                    localPopularMovies,
                    popularList,
                    goToNextPage,
                    isNetworkAvailable(context),
                    onLocalDataAvailable = { isLocalDataAvailable = it }
                ) {
                    ListContent(navController, it ,goToNextPage)
                    goToNextPage = false
                    viewModel.insertMovieDbResultPopular(it)
                }

                2 -> handleMovieContent(
                    navController,
                    localUpcomingMovies,
                    upcomingList,
                    goToNextPage,
                    isNetworkAvailable(context),
                    onLocalDataAvailable = { isLocalDataAvailable = it }

                ) {
                    ListContent(navController, it ,goToNextPage)
                    goToNextPage = false
                    viewModel.insertMovieDbResultUpcoming(it)
                }
            }
        }
    }
}

@Composable
fun <T> handleMovieContent(
    navController: NavController,
    localMovies: T?,
    remoteMovies: ApiState<T>,
    goToNextPage: Boolean,
    isNetworkAvailable: Boolean,
    onLocalDataAvailable: (Boolean) -> Unit,
    onSuccess: @Composable (T) -> Unit

) {
    when {
        localMovies != null -> {
            // Show local data if available
            onLocalDataAvailable(true)
            ListContent(navController, localMovies , goToNextPage)
        }
        isNetworkAvailable -> {
            // Fetch remote data when no local data is available and there's network access
            onLocalDataAvailable(false)
            ApiCallState(
                navController,
                remoteMovies,
                onSuccess = onSuccess
            )
        }
        else -> {
            // Show no internet message if there's no data and no network connection
            onLocalDataAvailable(false)
            displayNoInternetMessage()
        }
    }
}

