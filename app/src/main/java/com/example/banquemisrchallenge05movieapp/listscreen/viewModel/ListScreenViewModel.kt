package com.example.banquemisrchallenge05movieapp.listscreen.viewModel

import com.example.banquemisrchallenge05movieapp.utils.shared_viewmodel.BaseViewModel
import com.example.banquemisrchallenge05movieapp.utils.data_layer.Repository
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListScreenViewModel(val repository: Repository) : BaseViewModel() {
    private val _upcomingList = MutableStateFlow<ApiState<MovieDbResultUpcoming>>(ApiState.Loading)
    val upcomingList = _upcomingList.asStateFlow()

    private val _nowPlayingList = MutableStateFlow<ApiState<MovieDbResultNowPlaying>>(ApiState.Loading)
    val nowPlayingList = _nowPlayingList.asStateFlow()

    private val _popularList = MutableStateFlow<ApiState<MovieDbResultPopular>>(ApiState.Loading)
    val popularList = _popularList.asStateFlow()

    fun fetchNowPlayingList() {
        handleFetchList({ repository.fetchNowPlayingMovies() }, _nowPlayingList)
    }

    fun fetchPopularList() {
        handleFetchList({ repository.fetchPopularMovies() }, _popularList)
    }

    fun fetchUpcomingList() {
        handleFetchList({ repository.fetchUpcomingMovies() }, _upcomingList)
    }
}
