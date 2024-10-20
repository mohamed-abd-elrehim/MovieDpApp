package com.example.banquemisrchallenge05movieapp.utils.di

import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailViewModel
import com.example.banquemisrchallenge05movieapp.listscreen.viewModel.ListScreenViewModel
import com.example.banquemisrchallenge05movieapp.utils.data_layer.Repository
import com.example.banquemisrchallenge05movieapp.utils.data_layer.RepositoryImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.LocalDataSource
import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.LocalDataSourceImpl
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.RemoteDataSource
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.RemoteDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single<RemoteDataSource> { RemoteDataSourceImpl.getInstance() }
    single<LocalDataSource> { LocalDataSourceImpl.getInstance(context = androidContext())}
    single<Repository> { RepositoryImpl.getInstance(get(), get()) }
    viewModel { ListScreenViewModel(get() ) }
    viewModel { DetailViewModel(get()) }
}