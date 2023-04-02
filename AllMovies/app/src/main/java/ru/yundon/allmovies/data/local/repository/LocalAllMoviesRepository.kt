package ru.yundon.allmovies.data.local.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import ru.yundon.allmovies.data.local.database.AllMoviesDatabase
import ru.yundon.allmovies.data.local.database.MoviesDao
import ru.yundon.allmovies.data.local.database.AllMoviesListEntity

class LocalAllMoviesRepository(context: Context): IsFavoritesHelper {

    private val moviesDaoPopular: MoviesDao = AllMoviesDatabase.buildPopularDatabase(context).moviesDao()
    private val moviesDaoUpcoming: MoviesDao = AllMoviesDatabase.buildDatabaseUpcomingMovies(context).moviesDao()
    private val moviesDaoFavorites: MoviesDao = AllMoviesDatabase.buildFavoritesDatabase(context).moviesDao()

    private val moviesDaoSearch: MoviesDao = AllMoviesDatabase.buildSearchDatabase(context).moviesDao()

    override suspend fun isFavoritesAllMovieStatus(id: Int) {
        val movie: AllMoviesListEntity = getFavoritesMovieById(id)

        if (movie.isFavourite){
            movie.isFavourite = false
            updatePopularElement(movie)
            updateUpcomingElement(movie)
            deleteFavoritesElement(movie)
        }
    }

    override suspend fun isFavoritesAllMoviePopular(id: Int) {
        val movie: AllMoviesListEntity = getPopularMovieById(id)
        if (!movie.isFavourite){
            movie.isFavourite = true
            updatePopularElement(movie)
            updateUpcomingElement(movie)
            insertFavoritesElement(movie)
        } else {
            movie.isFavourite = false
            updatePopularElement(movie)
            updateUpcomingElement(movie)
            deleteFavoritesElement(movie)
        }
    }

    override suspend fun isFavoritesAllMovieUpcoming(id: Int) {
        val movie: AllMoviesListEntity = getUpcomingMovieById(id)
        if (!movie.isFavourite){
            movie.isFavourite = true
            updateUpcomingElement(movie)
            updatePopularElement(movie)
            insertFavoritesElement(movie)
        } else {
            movie.isFavourite = false
            updateUpcomingElement(movie)
            updatePopularElement(movie)
            deleteFavoritesElement(movie)
        }
    }

    override suspend fun isFavoritesAllMovieSearch(id: Int) {
        val movie: AllMoviesListEntity = getSearchAllMoviesById(id)
        if (!movie.isFavourite){
            movie.isFavourite = true
            updateUpcomingElement(movie)
            updatePopularElement(movie)
            updateSearchAllMoviesElement(movie)
            insertFavoritesElement(movie)
        }else {
            movie.isFavourite = false
            updateUpcomingElement(movie)
            updatePopularElement(movie)
            updateSearchAllMoviesElement(movie)
            deleteFavoritesElement(movie)
        }
    }


    fun getPopularListMovies(): Flow<List<AllMoviesListEntity>> {
        return moviesDaoPopular.getListMovies()
    }

    suspend fun getPopularListAllMovies(): List<AllMoviesListEntity> {
        return moviesDaoPopular.getListAllMovies()
    }

    suspend fun insertPopularMovies(list: List<AllMoviesListEntity>) {
        moviesDaoPopular.insertMovies(list)
    }

    suspend fun getPopularMovieById(id: Int): AllMoviesListEntity {
        return moviesDaoPopular.getMovieById(id)
    }

    private suspend fun updatePopularElement(model: AllMoviesListEntity) {
        moviesDaoPopular.updateElement(model)
    }

    fun getUpcomingListMovies(): Flow<List<AllMoviesListEntity>> {
        return moviesDaoUpcoming.getListMovies()
    }

    suspend fun getUpcomingListAllMovies(): List<AllMoviesListEntity> {
        return moviesDaoUpcoming.getListAllMovies()
    }

    suspend fun getUpcomingMovieById(id: Int): AllMoviesListEntity {
        return moviesDaoUpcoming.getMovieById(id)
    }

    suspend fun insertUpcomingMovies(list: List<AllMoviesListEntity>) {
        moviesDaoUpcoming.insertMovies(list)
    }

    private suspend fun updateUpcomingElement(model: AllMoviesListEntity) {
        moviesDaoUpcoming.updateElement(model)
    }

    fun getFavoritesListMovies(): Flow<List<AllMoviesListEntity>> {
        return moviesDaoFavorites.getListMovies()
    }

    private suspend fun insertFavoritesElement(model: AllMoviesListEntity) {
        moviesDaoFavorites.insertElement(model)
    }

    suspend fun getFavoritesMovieById(id: Int): AllMoviesListEntity {
        return moviesDaoFavorites.getMovieById(id)
    }

    private suspend fun deleteFavoritesElement(model: AllMoviesListEntity) {
        moviesDaoFavorites.deleteElement(model)
    }

    fun getSearchListAllMoviesFlow(): Flow<List<AllMoviesListEntity>> {
        return moviesDaoSearch.getListMovies()
    }

    suspend fun getSearchAllMoviesById(id: Int): AllMoviesListEntity {
        return moviesDaoSearch.getMovieById(id)
    }

    suspend fun insertSearchAllMovies(list: List<AllMoviesListEntity>){
        moviesDaoSearch.insertMovies(list)
    }

    private suspend fun updateSearchAllMoviesElement(model: AllMoviesListEntity){
        moviesDaoSearch.updateElement(model)
    }

    suspend fun deleteSearchAllMovies(){
        moviesDaoSearch.deleteAll()
    }

    fun searchUpcomingMoviesInDatabase(searchQuery: String): Flow<List<AllMoviesListEntity>>{
        return moviesDaoUpcoming.searchInDatabase(searchQuery)
    }

    fun searchPopularMoviesInDatabase(searchQuery: String): Flow<List<AllMoviesListEntity>>{
        return moviesDaoPopular.searchInDatabase(searchQuery)
    }
}