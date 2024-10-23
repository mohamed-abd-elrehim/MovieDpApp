package com.example.banquemisrchallenge05movieapp.detailscreen.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailViewModel
import com.example.banquemisrchallenge05movieapp.utils.data_layer.FakeRepositoryImpl
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.test_utils.forapicalls.movieDetailsList
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DetailScreenTest {

    lateinit var viewModel: DetailViewModel
    private lateinit var fakeRepository: FakeRepositoryImpl
    val expectedMovieDetails = movieDetailsList[0]

    @Before
    fun setUp() {
        fakeRepository = FakeRepositoryImpl()
        viewModel = DetailViewModel(fakeRepository)
    }

    @get:Rule
    val composeTestRule = createComposeRule()



    @Test
    fun testDetailScreenDisplaysMovieDetails() {
        composeTestRule.setContent {
            // Create a composable context
            val navController = rememberNavController()
            DetailScreen(navController, viewModel, movieId = "1")
        }

        val movieDetails = (viewModel.movieDetails.value as ApiState.Success).data


        composeTestRule.onNodeWithText(expectedMovieDetails.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedMovieDetails.overview).assertIsDisplayed()
    }



}