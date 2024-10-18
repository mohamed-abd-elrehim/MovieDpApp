package com.example.banquemisrchallenge05movieapp.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.listscreen.view.ListScreen
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModelFactory
import com.example.banquemisrchallenge05movieapp.utils.constants.NavigationKeys


sealed class Screen(val route: String, val title: Int) {

    object ListScreen : Screen(NavigationKeys.ListScreen, R.string.list_screen)
    object DetailScreen : Screen(NavigationKeys.DetailScreen, R.string.detail_screen)
    object SplashScreen : Screen(NavigationKeys.SplashScreen, R.string.splash_screen)

    @Composable
    fun AppNavigation(
        navController: NavHostController,
        listScreenViewModelFactory: ListScreenViewModelFactory
    ){

        NavHost(navController, startDestination = Screen.ListScreen.route) {
            composable(route = Screen.ListScreen.route) {
                val viewModel: ListScreenViewModel = viewModel(factory =listScreenViewModelFactory)
                ListScreen(viewModel)
            }


        }

    }
}