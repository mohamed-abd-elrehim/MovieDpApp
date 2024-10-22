package com.example.banquemisrchallenge05movieapp.startscreen.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailScreenViewModelFactory
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailViewModel
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModelFactory
import com.example.banquemisrchallenge05movieapp.mainhome.view.MainHomeScreen
import com.example.banquemisrchallenge05movieapp.startscreen.components.SplashLottie
import com.example.banquemisrchallenge05movieapp.utils.data_layer.RepositoryImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.LocalDataSourceImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.RemoteDataSourceImpl
import com.example.banquemisrchallenge05movieapp.utils.di.appModule
import com.example.banquemisrchallenge05movieapp.utils.navigation.Screen
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.InternetChecker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.getViewModel
import org.koin.core.context.startKoin


class MainActivity : ComponentActivity() {
    private lateinit var internetChecker: InternetChecker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internetChecker = InternetChecker(this)
        internetChecker.startMonitoring()

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        setContent {
            MaterialTheme {
                Surface {

                    val navController = rememberNavController()

                  NavHost(navController, startDestination = Screen.SplashScreen.route) {
                          composable(Screen.SplashScreen.route) { SplashLottie(navController) }

                          composable(Screen.ListScreen.route) {
                              val listScreenViewModel: ListScreenViewModel = getViewModel()
                              val detailScreenViewModel: DetailViewModel = getViewModel()

                              MainHomeScreen(
                                  listScreenViewModel,
                                  detailScreenViewModel
                              )

                          }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        internetChecker.stopMonitoring()
    }
}

