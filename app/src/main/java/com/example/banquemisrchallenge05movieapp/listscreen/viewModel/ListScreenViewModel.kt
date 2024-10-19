package com.example.banquemisrchallenge05movieapp.listscreen.viewModel

import androidx.lifecycle.viewModelScope
import com.example.banquemisrchallenge05movieapp.utils.shared_viewmodel.BaseViewModel
import com.example.banquemisrchallenge05movieapp.utils.data_layer.Repository
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListScreenViewModel(private val repository: Repository) : BaseViewModel() {
    private val _upcomingList = MutableStateFlow<ApiState<MovieDbResultUpcoming>>(ApiState.Loading)
    val upcomingList = _upcomingList.asStateFlow()

    private val _nowPlayingList =
        MutableStateFlow<ApiState<MovieDbResultNowPlaying>>(ApiState.Loading)
    val nowPlayingList = _nowPlayingList.asStateFlow()

    private val _popularList = MutableStateFlow<ApiState<MovieDbResultPopular>>(ApiState.Loading)
    val popularList = _popularList.asStateFlow()

    private val _totalPages = MutableStateFlow<Int>(0)
    val totalPages = _totalPages.asStateFlow()

    fun fetchNowPlayingList(page: Int) {
        handleFetchList(
            { repository.fetchNowPlayingMovies(page = page) },
            _nowPlayingList,
            _totalPages
        )
    }

    fun fetchPopularList(page: Int) {
        handleFetchList({ repository.fetchPopularMovies(page = page) }, _popularList, _totalPages)
    }

    fun fetchUpcomingList(page: Int) {
        handleFetchList({ repository.fetchUpcomingMovies(page = page) }, _upcomingList, _totalPages)
    }


    fun insertMovieDbResultPopular(movie: MovieDbResultPopular) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovieDbResultPopular(movie)
        }
    }

    fun insertMovieDbResultUpcoming(movie: MovieDbResultUpcoming) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovieDbResultUpcoming(movie)
        }
    }

    fun insertMovieDbResultNowPlaying(movie: MovieDbResultNowPlaying) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovieDbResultNowPlaying(movie)
        }
    }


    fun getPopularMovies(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovies(page)
        }

    }

    fun getUpcomingMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUpcomingMovies(page)
        }
    }

    fun getNowPlayingMovies(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNowPlayingMovies(page)
        }
    }
    /*

        fun getMovieDetails(movieId: Int): MovieDetails? {
            return movieDao.getMovieDetails(movieId)
        }

        fun insertMovieDetails(movie: MovieDetails) {
            movieDao.insertMovieDetails(movie)
        }
    */

}
