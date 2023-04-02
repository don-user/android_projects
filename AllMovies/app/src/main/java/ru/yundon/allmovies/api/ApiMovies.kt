package ru.yundon.allmovies.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yundon.allmovies.api.model.MoviesData

interface ApiMovies {
    @GET("movie/popular")
    suspend fun getPopularMovies( @Query("api_key") apiKey : String,
                                  @Query("language") language: String,
                                  @Query("page") page: Int): Response<MoviesData>
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String,
                                  @Query("language") language: String,
                                  @Query("page") page: Int): Response<MoviesData>

    @GET("search/movie")
    suspend fun searchMovie( @Query("api_key") apiKey: String,
                     @Query("query") query: String,
                     @Query("language") language: String): Response<MoviesData>
}

