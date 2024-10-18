package com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.moviedb

import android.util.Log
import com.example.banquemisrchallenge05movieapp.utils.constants.APIKeys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviedbRetrofitHelper {

    val api: MoviedbAPIServices by lazy {
        // Create an interceptor to add the API key to requests
        val headerInterceptor = Interceptor { chain ->
            // Get the original request URL
            val originalUrl = chain.request().url

            // Build a new URL with the API key as a query parameter
            val urlWithApiKey = originalUrl.newBuilder()
                .addQueryParameter("api_key", APIKeys.MOVIEDB_API_KEY) // Add your API key here
                .build()

            // Build a new request with the updated URL
            val request = chain.request().newBuilder()
                .url(urlWithApiKey) // Use the new URL
                .build()

            // Log the request URL
            Log.d("MoviedbRetrofitHelper", "Requesting URL: ${request.url}")

            chain.proceed(request)
        }

        // Build the OkHttpClient with the interceptors
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor) // Add the API key interceptor

        // Logging Interceptor for logging the request and response
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        clientBuilder.addInterceptor(logging)

        // Create Retrofit instance
        Retrofit.Builder()
            .baseUrl(APIKeys.MOVIEDB_BASE_URL) // Ensure this ends with '/'
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviedbAPIServices::class.java)
    }
}
