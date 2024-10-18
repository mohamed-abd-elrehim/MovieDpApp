package com.example.banquemisrchallenge05movieapp.listscreen.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.utils.data_layer.Repository

class ListScreenViewModelFactory(
    val repository: Repository,
    val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListScreenViewModel::class.java)) {
            return ListScreenViewModel(repository) as T
        }
        throw IllegalArgumentException(context.getString(R.string.unknown_viewmodel_class))
    }


}
