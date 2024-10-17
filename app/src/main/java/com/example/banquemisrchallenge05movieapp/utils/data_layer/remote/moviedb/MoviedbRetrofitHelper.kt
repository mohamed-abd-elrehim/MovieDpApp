package com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.moviedb

import com.example.banquemisrchallenge05movieapp.utils.constants.APIKeys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviedbRetrofitHelper {

    val api: MoviedbAPIServices by lazy {

        val headerInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(APIKeys.AUTHORIZATION,APIKeys.MOVIEDB_ACCESS_TOKEN)
                .build()
            chain.proceed(request)
        }

        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)


        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(logging)


        val client = clientBuilder.build()

        Retrofit.Builder()
            .baseUrl(APIKeys.MOVIEDB_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviedbAPIServices::class.java)
    }
}





