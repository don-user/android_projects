package ru.yundon.allmovies.ui.fragments.favorites

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yundon.allmovies.data.local.repository.LocalAllMoviesRepository

class ViewModelFavoritesAllMovies(application: Application): AndroidViewModel(application) {

    private val repositoryFavorites = LocalAllMoviesRepository(application)

    private val _favorites = MutableLiveData<Boolean>()
    val favorites: LiveData<Boolean> = _favorites

    val listFavoritesMovies = repositoryFavorites.getFavoritesListMovies().asLiveData()

    fun favoritesStatus(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val movie = repositoryFavorites.getFavoritesMovieById(id).isFavourite
            _favorites.postValue(movie)

        }
    }

    fun updateFavoritesStatus(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryFavorites.isFavoritesAllMovieStatus(id)
        }
    }
}
