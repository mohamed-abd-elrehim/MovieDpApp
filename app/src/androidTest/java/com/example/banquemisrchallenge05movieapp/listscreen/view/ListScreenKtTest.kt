package com.example.banquemisrchallenge05movieapp.listscreen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.utils.constants.NavigationKeys
import com.example.banquemisrchallenge05movieapp.utils.data_layer.FakeRepositoryImpl
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test



class ListScreenKtTest{

    lateinit var viewModel: ListScreenViewModel
    private lateinit var fakeRepository: FakeRepositoryImpl


    @Before
    fun setUp() {
        fakeRepository = FakeRepositoryImpl()
        viewModel = ListScreenViewModel(fakeRepository)
        composeTestRule.setContent {
            ListScreen(navController = rememberNavController(), viewModel = viewModel)
        }
    }

    @get:Rule
    val composeTestRule = createComposeRule()



    @Test
    fun testListScreenClickPopular() {


        viewModel.setSelectedTabIndex(1)
        viewModel.setCurrentPage(1)
        composeTestRule.onNodeWithText(NavigationKeys.Popular).performClick()

    }

    @Test
    fun testListScreenClickUpcoming() {


        viewModel.setSelectedTabIndex(2)
        viewModel.setCurrentPage(2)

        composeTestRule.onNodeWithText(NavigationKeys.NowPlaying).performClick()

    }
    @Test
    fun testListScreenClickNowPlaying() {


        viewModel.setSelectedTabIndex(0)
        viewModel.setCurrentPage(0)

        composeTestRule.onNodeWithText(NavigationKeys.NowPlaying).performClick()

    }



}