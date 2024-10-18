package com.example.banquemisrchallenge05movieapp.listscreen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.ui.theme.PrimaryColor
import com.example.banquemisrchallenge05movieapp.utils.constants.NavigationKeys
import com.example.banquemisrchallenge05movieapp.listscreen.components.ApiCallState
import com.example.banquemisrchallenge05movieapp.listscreen.components.ListScreenSecondTitle

@Composable
fun ListScreen(viewModel: ListScreenViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(NavigationKeys.NowPlaying, NavigationKeys.Popular, NavigationKeys.Upcoming)

    val nowPlayingList by viewModel.nowPlayingList.collectAsStateWithLifecycle()
    val popularList by viewModel.popularList.collectAsStateWithLifecycle()
    val upcomingList by viewModel.upcomingList.collectAsStateWithLifecycle()



    LaunchedEffect(selectedTabIndex) {
        when (selectedTabIndex) {
            0 -> viewModel.fetchNowPlayingList()
            1 -> viewModel.fetchPopularList()
            2 -> viewModel.fetchUpcomingList()
        }
    }



    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.background(Color.Gray),
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            title,
                            color = if (selectedTabIndex == index) Color.White else Color.Black
                        )
                    },
                    onClick = { selectedTabIndex = index },
                    selected = selectedTabIndex == index,
                    modifier = Modifier.background
                        (if (selectedTabIndex == index) PrimaryColor else Color.Transparent)
                )
            }
        }

        when (selectedTabIndex) {
            0 -> {
                ListScreenSecondTitle("Now Playing Content")
                ApiCallState ( "Now Playing Content",nowPlayingList)

            }

            1 -> {
                ListScreenSecondTitle("Popular Content")
                ApiCallState ( "Popular Content",popularList)
                }

            2 -> {
                ListScreenSecondTitle("Upcoming Content")
                ApiCallState ( "Upcoming Content",upcomingList)
              }
        }
    }
}



