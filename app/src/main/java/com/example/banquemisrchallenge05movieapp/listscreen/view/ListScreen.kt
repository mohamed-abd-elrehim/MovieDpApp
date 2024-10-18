package com.example.banquemisrchallenge05movieapp.listscreen.view
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.ui.theme.PrimaryColor
import com.example.banquemisrchallenge05movieapp.utils.constants.NavigationKeys
import com.example.banquemisrchallenge05movieapp.listscreen.components.ApiCallState
@Composable
fun ListScreen(viewModel: ListScreenViewModel) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(NavigationKeys.NowPlaying, NavigationKeys.Popular, NavigationKeys.Upcoming)

    val nowPlayingList by viewModel.nowPlayingList.collectAsStateWithLifecycle()
    val popularList by viewModel.popularList.collectAsStateWithLifecycle()
    val upcomingList by viewModel.upcomingList.collectAsStateWithLifecycle()

    var currentPage by remember { mutableIntStateOf(1) }
    val totalPages by viewModel.totalPages.collectAsStateWithLifecycle()

    LaunchedEffect(selectedTabIndex, currentPage) {
        when (selectedTabIndex) {
            0 -> viewModel.fetchNowPlayingList(page = currentPage)
            1 -> viewModel.fetchPopularList(page = currentPage)
            2 -> viewModel.fetchUpcomingList(page = currentPage)
        }
    }




    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "List Screen",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        )

        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .background(Color.White)
        ) {
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
                    onClick = {
                        selectedTabIndex =
                            index // Notify the parent to update the selected tab index
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

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> {
                ApiCallState(
                    "Now Playing Content",
                    nowPlayingList,
                    currentPage,
                    totalPages,
                    onPageChange = {
                        currentPage = it
                    })

            }
            1 -> {
                ApiCallState(
                    "Popular Content",
                    popularList,
                    currentPage,
                    totalPages,
                    onPageChange = {
                        currentPage = it
                    })
            }

            2 -> {
                ApiCallState(
                    "Upcoming Content",
                    upcomingList,
                    currentPage,
                    totalPages,
                    onPageChange = {
                        currentPage = it
                    })

            }
        }

    }

}


