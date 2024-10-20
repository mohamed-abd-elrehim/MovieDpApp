package com.example.banquemisrchallenge05movieapp.mainhome.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailScreenViewModelFactory
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailViewModel
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModelFactory
import com.example.banquemisrchallenge05movieapp.utils.navigation.Screen.DetailScreen.AppNavigation


@Composable
fun MainHomeScreen(
    listScreenViewModel: ListScreenViewModel,
    detailScreenViewModel: DetailViewModel

) {


    val navController = rememberNavController()

    Scaffold(
        containerColor = Color.White,

        ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AppNavigation(
                navController = navController,
                listScreenViewModel = listScreenViewModel,
                detailScreenViewModel = detailScreenViewModel
            )

        }

    }


}