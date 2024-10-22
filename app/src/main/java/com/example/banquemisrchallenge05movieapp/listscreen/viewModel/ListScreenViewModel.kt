package com.example.banquemisrchallenge05movieapp.listscreen.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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


    private val _localUpcomingList = MutableStateFlow<MovieDbResultUpcoming?>(null)
    val localUpcomingList = _localUpcomingList.asStateFlow()

    private val _localNowPlayingList =
        MutableStateFlow<MovieDbResultNowPlaying?>(null)
    val localNowPlayingList = _localNowPlayingList.asStateFlow()

    private val _localPopularList = MutableStateFlow<MovieDbResultPopular?>(null)
    val localPopularList = _localPopularList.asStateFlow()

    private val _totalPages = MutableStateFlow<Int>(0)
    val totalPages = _totalPages.asStateFlow()

    private var _currentPage = MutableStateFlow<Int>(1)
    val currentPage = _currentPage.asStateFlow()

    fun setCurrentPage(page: Int) {
        _currentPage.value = page
    }

    private var _selectedTabIndex = MutableStateFlow<Int>(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    fun setSelectedTabIndex(page: Int) {
        _selectedTabIndex.value = page
    }

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


    fun getPopularMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovies(page).collect {
                _localPopularList.value = it
                if (it != null) {
                    _totalPages.value = it.total_pages
                }
            }
        }

    }

    fun getUpcomingMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUpcomingMovies(page).collect {
                _localUpcomingList.value = it
                if (it != null) {
                    _totalPages.value = it.total_pages
                }
            }
        }
    }

    fun getNowPlayingMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNowPlayingMovies(page).collect {
                _localNowPlayingList.value = it
                if (it != null) {
                    _totalPages.value = it.total_pages
                }
            }
        }
    }


    fun getLocalMoviesIfAvailable(selectedTabIndex: Int, currentPage: Int) {
        when (selectedTabIndex) {
            0 -> {
                getNowPlayingMovies(currentPage)
            }

            1 -> {
                getPopularMovies(currentPage)
            }

            2 -> {
                getUpcomingMovies(currentPage)
            }
        }
    }


    fun fetchRemoteMoviesIfLocalIsEmpty(selectedTabIndex: Int, currentPage: Int) {
        when (selectedTabIndex) {
            0 -> {
                if (_localNowPlayingList.value == null) {
                    fetchNowPlayingList(currentPage)
                }
            }

            1 -> {
                if (_localPopularList.value == null) {
                    fetchPopularList(currentPage)
                }

            }

            2 -> {
                if (_localUpcomingList.value == null) {
                    fetchUpcomingList(currentPage)
                }
            }
        }
    }




}

