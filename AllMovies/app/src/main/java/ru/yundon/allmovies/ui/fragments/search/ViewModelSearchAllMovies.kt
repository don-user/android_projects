package ru.yundon.allmovies.ui.fragments.search

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yundon.allmovies.data.local.repository.AllMoviesRemoteDataSource
import ru.yundon.allmovies.data.local.repository.LocalAllMoviesRepository

class ViewModelSearchAllMovies(application: Application): AndroidViewModel(application) {

    private val remoteDataSource = AllMoviesRemoteDataSource(application)
    private val repositorySearchAllMovies = LocalAllMoviesRepository(application)

    val liveDataSearchAllMovies = repositorySearchAllMovies.getSearchListAllMoviesFlow().asLiveData()


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _favorites = MutableLiveData<Boolean>()
    val favorites: LiveData<Boolean> = _favorites

    fun searchAllMovie(search: String){
        viewModelScope.launch (Dispatchers.IO) {
            _isLoading.postValue(true)
            remoteDataSource.searchAllMoviesList(search)
            _isLoading.postValue(false)
        }
    }

    fun deleteSearchTable(){
        viewModelScope.launch (Dispatchers.IO)  {
            repositorySearchAllMovies.deleteSearchAllMovies()
        }
    }

    fun updateFavoritesStatusSearch(id: Int){
        viewModelScope.launch (Dispatchers.IO) {
            repositorySearchAllMovies.isFavoritesAllMovieSearch(id)
            val movie = repositorySearchAllMovies.getSearchAllMoviesById(id).isFavourite
            _favorites.postValue(movie)
        }
    }

}