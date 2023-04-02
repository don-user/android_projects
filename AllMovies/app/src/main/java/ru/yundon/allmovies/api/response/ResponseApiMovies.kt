package ru.yundon.allmovies.api.response

import android.graphics.Bitmap
import com.squareup.picasso.Picasso
import retrofit2.Response
import ru.yundon.allmovies.R
import ru.yundon.allmovies.ui.main.MainActivity.Companion.API_KEY
import ru.yundon.allmovies.api.MoviesApiClient
import ru.yundon.allmovies.api.model.MoviesData
import ru.yundon.allmovies.utils.Constants.LANGUAGE_RU

class ResponseApiMovies {
    suspend fun getPopularMoviesList(page: Int): MoviesData? {
        val response: Response<MoviesData>
        return try {
            response = MoviesApiClient.apiClient.getPopularMovies(API_KEY, LANGUAGE_RU, page)
            response.body()
        }catch (e: Exception){
            null
        }
    }

    suspend fun getUpcomingMovies(page: Int): MoviesData?{
        return try {
            val responseNowPlayingMovies = MoviesApiClient.apiClient.getUpcomingMovies(API_KEY, LANGUAGE_RU, page)
            responseNowPlayingMovies.body()
        }catch (e: Exception){
            null
        }
    }

    suspend fun searchAllMovies(query: String): MoviesData?{
        return try {
            val responseSearchAllMovies = MoviesApiClient.apiClient.searchMovie(API_KEY, query, LANGUAGE_RU)
            responseSearchAllMovies.body()
        }catch (e: Exception){
            null
        }
    }

    fun getImageFromApi(urlImage: String): Bitmap?{
        var image: Bitmap? = null
        return try {
            image = Picasso.get()
                .load(urlImage)
                .placeholder(R.drawable.ic_launcher_foreground)
                .get()
            image
        }    catch (e: Exception){
            image
        }
    }
}