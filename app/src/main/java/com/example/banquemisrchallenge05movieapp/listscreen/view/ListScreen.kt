package com.example.banquemisrchallenge05movieapp.listscreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel

@Composable
fun ListScreen(viewModel: ListScreenViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Now Playing", "Popular", "Upcoming")

    Column() {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(

                    text = { Text(title) },
                    onClick = { selectedTabIndex = index },
                    selected = selectedTabIndex == index,
                )
            }
            when (selectedTabIndex) {

            }
        }


    }


}

