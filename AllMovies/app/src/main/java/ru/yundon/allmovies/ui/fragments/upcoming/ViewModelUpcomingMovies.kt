package ru.yundon.allmovies.ui.fragments.upcoming

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yundon.allmovies.data.local.database.AllMoviesDatabase
import ru.yundon.allmovies.data.local.database.AllMoviesListEntity
import ru.yundon.allmovies.data.local.repository.LocalAllMoviesRepository
import ru.yundon.allmovies.data.local.repository.AllMoviesRemoteDataSource

class ViewModelUpcomingMovies(application: Application): AndroidViewModel(application){

    private val repositoryUpcoming = LocalAllMoviesRepository(application)
    private val remoteDataSource = AllMoviesRemoteDataSource(application)


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _favorites = MutableLiveData<Boolean>()
    val favorites: LiveData<Boolean> = _favorites

    val listUpcomingMovies: LiveData<List<AllMoviesListEntity>> = repositoryUpcoming.getUpcomingListMovies().asLiveData()

    init {
        viewModelScope.launch (Dispatchers.IO){
            _isLoading.postValue(true)
            remoteDataSource.upcomingMoviesList(1)
            _isLoading.postValue(false)
        }
    }

    fun updateFavoritesStatusUpcoming(id: Int){
        viewModelScope.launch (Dispatchers.IO){
            repositoryUpcoming.isFavoritesAllMovieUpcoming(id)
            val movie = repositoryUpcoming.getUpcomingMovieById(id).isFavourite
            _favorites.postValue(movie)
        }
    }

    fun nextPageUpcomingMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = repositoryUpcoming.getUpcomingListAllMovies()
            var pageMax = 1
            for (i in list){
                if (i.page > pageMax) pageMax = i.page
            }
            val totalPage = list.last().totalPages

            if (pageMax < totalPage) pageMax += 1
            _isLoading.postValue(true)
            remoteDataSource.upcomingMoviesList(pageMax)
            _isLoading.postValue(false)
        }
    }

    fun searchUpcomingMovie(search: String): LiveData<List<AllMoviesListEntity>>{
        return repositoryUpcoming.searchUpcomingMoviesInDatabase(search).asLiveData()
    }
}