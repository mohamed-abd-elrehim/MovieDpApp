package com.example.banquemisrchallenge05movieapp.utils.data_layer.local


class LocalDataSourceImpl private constructor() : LocalDataSource {

    companion object {
        @Volatile
        private var instance: LocalDataSourceImpl? = null

        fun getInstance(): LocalDataSourceImpl {
            return instance ?: synchronized(this) {
                instance ?: LocalDataSourceImpl().also { instance = it }
            }
        }
    }

}