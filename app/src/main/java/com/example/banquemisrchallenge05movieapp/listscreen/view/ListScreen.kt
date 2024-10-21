package com.example.banquemisrchallenge05movieapp.listscreen.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.banquemisrchallenge05movieapp.listscreen.components.ApiCallState
import com.example.banquemisrchallenge05movieapp.listscreen.components.ListContent
import com.example.banquemisrchallenge05movieapp.listscreen.components.Pagination
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.utils.shared_components.Gap
import com.example.banquemisrchallenge05movieapp.utils.shared_components.HeaderText
import com.example.banquemisrchallenge05movieapp.utils.shared_components.LottieWithText
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

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(NavigationKeys.NowPlaying, NavigationKeys.Popular, NavigationKeys.Upcoming)
    val totalPages by viewModel.totalPages.collectAsStateWithLifecycle()
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val isNetworkAvailable = isNetworkAvailable(context)

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
        modifier = Modifier.background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            HeaderText(headerText = "List Screen")
        }

        Gap(height = 16)
        TabRow(selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White),
            indicator = {},
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
                    onClick = { selectedTabIndex = index },
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
                    isNetworkAvailable(context),
                    onLocalDataAvailable = { isLocalDataAvailable = it }
                ) {
                    viewModel.insertMovieDbResultNowPlaying(it)
                }

                1 -> handleMovieContent(
                    navController,
                    localPopularMovies,
                    popularList,
                    isNetworkAvailable(context),
                    onLocalDataAvailable = { isLocalDataAvailable = it }
                ) {
                    viewModel.insertMovieDbResultPopular(it)
                }

                2 -> handleMovieContent(
                    navController,
                    localUpcomingMovies,
                    upcomingList,
                    isNetworkAvailable(context),
                    onLocalDataAvailable = { isLocalDataAvailable = it }

                ) {
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
    isNetworkAvailable: Boolean,
    onLocalDataAvailable: (Boolean) -> Unit,
    onSuccess: @Composable (T) -> Unit

) {
    when {
        localMovies != null -> {
            // Show local data if available
            onLocalDataAvailable(true)
            ListContent(navController, localMovies)
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

@Composable
fun displayNoInternetMessage() {
    Gap(height = 16)
    LottieWithText(
        R.raw.no_internet,
        stringResource(R.string.no_internet_connection)
    )
}
