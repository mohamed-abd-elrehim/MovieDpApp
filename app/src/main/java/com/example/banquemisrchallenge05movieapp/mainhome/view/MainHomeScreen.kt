package com.example.banquemisrchallenge05movieapp.mainhome.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModelFactory
import com.example.banquemisrchallenge05movieapp.utils.navigation.Screen
import com.example.banquemisrchallenge05movieapp.utils.navigation.Screen.DetailScreen.AppNavigation


@Composable
fun MainHomeScreen(
    listScreenViewModelFactory: ListScreenViewModelFactory

) {

    val navController = rememberNavController()

    Scaffold(
        containerColor = Color.White,

        ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AppNavigation(
                navController = navController,
                listScreenViewModelFactory = listScreenViewModelFactory
            )

        }

    }


}