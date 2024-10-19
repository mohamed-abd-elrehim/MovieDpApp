package com.example.banquemisrchallenge05movieapp.listscreen.view

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.ui.theme.PrimaryColor
import com.example.banquemisrchallenge05movieapp.utils.constants.NavigationKeys
import com.example.banquemisrchallenge05movieapp.listscreen.components.ApiCallState
import com.example.banquemisrchallenge05movieapp.listscreen.components.ListContent
import com.example.banquemisrchallenge05movieapp.listscreen.components.Pagination
import com.example.banquemisrchallenge05movieapp.utils.shared_components.Gap

@Composable
fun ListScreen(navController: NavHostController, viewModel: ListScreenViewModel) {
    val nowPlayingList by viewModel.nowPlayingList.collectAsStateWithLifecycle()
    val popularList by viewModel.popularList.collectAsStateWithLifecycle()
    val upcomingList by viewModel.upcomingList.collectAsStateWithLifecycle()

    val localPopularMovies by viewModel.localPopularList.collectAsStateWithLifecycle()
    val localNowPlayingMovies by viewModel.localNowPlayingList.collectAsStateWithLifecycle()
    val localUpcomingMovies by viewModel.localUpcomingList.collectAsStateWithLifecycle()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(NavigationKeys.NowPlaying, NavigationKeys.Popular, NavigationKeys.Upcoming)
    var currentPage by remember { mutableIntStateOf(1) }
    val totalPages by viewModel.totalPages.collectAsStateWithLifecycle()

    LaunchedEffect(selectedTabIndex, currentPage) {
        when (selectedTabIndex) {
            0 -> {
                viewModel.getNowPlayingMovies(currentPage)
            }

            1 -> {
                viewModel.getPopularMovies(currentPage)
            }

            2 -> {
                viewModel.getUpcomingMovies(currentPage)
            }
        }
    }

    LaunchedEffect(selectedTabIndex, currentPage) {
        when (selectedTabIndex) {
            0 -> {
                if (localNowPlayingMovies == null) {
                    viewModel.fetchNowPlayingList(currentPage)
                }
            }

            1 -> {
                if (localPopularMovies == null) {
                    viewModel.fetchPopularList(currentPage)
                }

            }

            2 -> {
                if (localUpcomingMovies == null) {
                    viewModel.fetchUpcomingList(currentPage)
                }
            }
        }
    }

    Column(
        modifier = Modifier.background(Color.White)
    ) {
        TopAppBar(backgroundColor = Color.White, title = {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    "List Screen",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
        })

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
        Pagination(currentPage = currentPage, totalPages = totalPages, onPageChange = {
            currentPage = it
        })

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> {
                if (localNowPlayingMovies == null) {
                    ApiCallState(navController, nowPlayingList, onSuccess = {
                        viewModel.insertMovieDbResultNowPlaying(it)
                    })
                } else {
                    ListContent(navController, localNowPlayingMovies)
                }
            }

            1 -> {
                if (localPopularMovies == null) {
                    ApiCallState(navController, popularList, onSuccess = {
                        viewModel.insertMovieDbResultPopular(it)
                    })
                } else {
                    ListContent(navController, localPopularMovies)
                }
            }

            2 -> {
                if (localUpcomingMovies == null) {
                    ApiCallState(navController, upcomingList, onSuccess = {
                        viewModel.insertMovieDbResultUpcoming(it)
                    })
                } else {
                    ListContent(navController, localUpcomingMovies)
                }
            }
        }
    }
}

