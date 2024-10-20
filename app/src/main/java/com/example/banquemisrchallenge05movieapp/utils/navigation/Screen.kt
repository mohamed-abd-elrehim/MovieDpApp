package com.example.banquemisrchallenge05movieapp.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.detailscreen.view.DetailScreen
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailScreenViewModelFactory
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailViewModel
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
        listScreenViewModel: ListScreenViewModel,
        detailScreenViewModel: DetailViewModel
    ){

        NavHost(navController, startDestination = Screen.ListScreen.route) {
            composable(route = Screen.ListScreen.route) {
                ListScreen(navController,listScreenViewModel)
            }


            composable(
                route = NavigationKeys.DetailScreen + "/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                // Get the movieId from the arguments
                val movieId = backStackEntry.arguments?.getInt("movieId")

                if (movieId != null) {
                    DetailScreen(navController, viewModel = detailScreenViewModel,movieId = movieId)
                }
            }


        }

    }
}