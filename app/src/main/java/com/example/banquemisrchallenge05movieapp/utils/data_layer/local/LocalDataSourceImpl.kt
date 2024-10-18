package com.example.banquemisrchallenge05movieapp.utils.data_layer.local

import android.content.Context


class LocalDataSourceImpl private constructor(context: Context) : LocalDataSource {


    companion object {
        @Volatile
        private var instance: LocalDataSourceImpl? = null

        fun getInstance(context: Context): LocalDataSourceImpl {
            return instance ?: synchronized(this) {
                instance ?: LocalDataSourceImpl(context).also { instance = it }
            }
        }
    }

}