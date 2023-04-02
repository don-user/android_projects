package ru.yundon.allmovies.data.local.repository

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.yundon.allmovies.api.model.MoviesData
import ru.yundon.allmovies.utils.Constants.ERROR_CONNECTION
import ru.yundon.allmovies.api.model.MoviesInfo
import ru.yundon.allmovies.api.response.ResponseApiMovies
import ru.yundon.allmovies.data.local.database.AllMoviesListEntity

class AllMoviesRemoteDataSource(private val context: Context) {

    private val localMoviesRepository = LocalAllMoviesRepository(context)
    private val responseApi = ResponseApiMovies()

    suspend fun popularMoviesList(page: Int) {
        val mList = responseApi.getPopularMoviesList(page)
        if (mList != null) {
            val moviesEntityList = listTransformation(mList.results, mList)
            localMoviesRepository.insertPopularMovies(moviesEntityList)  //добавление в базу данных таблица популярные фильмы
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, ERROR_CONNECTION, Toast.LENGTH_LONG).show() //если нет соеденения или ошибка от сервера то сообщение
            }
        }
    }

    suspend fun upcomingMoviesList(page: Int) {
        val mList = responseApi.getUpcomingMovies(page)
        if (mList != null) {
            val moviesEntityList = listTransformation(mList.results, mList)
            localMoviesRepository.insertUpcomingMovies(moviesEntityList)  //добавление в базу данных таблица скоро в кино фильмы
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, ERROR_CONNECTION, Toast.LENGTH_LONG).show()
            }
        }
    }

    suspend fun searchAllMoviesList(sentQuery: String) {
        val mList = responseApi.searchAllMovies(sentQuery)
        if (mList != null) {
            val moviesEntityList = listTransformation(mList.results, mList)
            localMoviesRepository.insertSearchAllMovies(moviesEntityList)
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, ERROR_CONNECTION, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun listTransformation(list: List<MoviesInfo>, json: MoviesData): List<AllMoviesListEntity> {
        val listTrans = list.map {
            AllMoviesListEntity(
                it.id,
                it.posterPath,
                it.overview,
                it.releaseDate,
                it.originalTitle,
                it.originalLanguage,
                it.title,
                it.backdropPath,
                it.popularity,
                it.voteCount,
                it.voteAverage,
                totalPages = 15,
                isFavourite = false,
                page = json.page.toInt()
            )
        }
        return listTrans
    }

    suspend fun getImage(urlImage: String): Bitmap? {
        val image = responseApi.getImageFromApi(urlImage)
        if (image != null) {
            return image
        } else {
                withContext(Dispatchers.Main) {
                Toast.makeText(context, ERROR_CONNECTION, Toast.LENGTH_LONG).show()
            }
            return null
        }
    }
}