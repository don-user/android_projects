package ru.yundon.allmovies.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.yundon.allmovies.utils.Constants.BASE_URL


object MoviesApiClient {

    val apiClient: ApiMovies by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@lazy retrofit.create(ApiMovies::class.java)
    }
}