package ru.yundon.allmovies.ui.fragments.popular

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yundon.allmovies.data.local.database.AllMoviesListEntity
import ru.yundon.allmovies.data.local.repository.LocalAllMoviesRepository
import ru.yundon.allmovies.data.local.repository.AllMoviesRemoteDataSource

class ViewModelPopularAllMovies(application: Application): AndroidViewModel(application) {

    private val repositoryPopular = LocalAllMoviesRepository(application)
    private val remoteDataSource = AllMoviesRemoteDataSource(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _favorites = MutableLiveData<Boolean>()
    val favorites: LiveData<Boolean> = _favorites

    val listPopularMovies = repositoryPopular.getPopularListMovies().asLiveData()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            remoteDataSource.popularMoviesList(1)
            _isLoading.postValue(false)
        }
    }

    fun updateFavoritesStatusPopular(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryPopular.isFavoritesAllMoviePopular(id)

            val movie = repositoryPopular.getPopularMovieById(id).isFavourite
            _favorites.postValue(movie)
        }
    }

    fun nextPagePopularMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = repositoryPopular.getPopularListAllMovies()
            var pageMax = 1
            for (i in list){
                if (i.page > pageMax) pageMax = i.page
            }
            val totalPage = list.last().totalPages

            if (pageMax < totalPage) pageMax += 1
            _isLoading.postValue(true)
            remoteDataSource.popularMoviesList(pageMax)
            _isLoading.postValue(false)
        }
    }

    fun searchPopularMovie(search: String): LiveData<List<AllMoviesListEntity>>{
        return repositoryPopular.searchPopularMoviesInDatabase(search).asLiveData()
    }
}