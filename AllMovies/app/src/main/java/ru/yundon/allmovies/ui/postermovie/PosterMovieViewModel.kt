package ru.yundon.allmovies.ui.postermovie

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yundon.allmovies.data.local.database.AllMoviesListEntity
import ru.yundon.allmovies.data.local.repository.LocalAllMoviesRepository
import ru.yundon.allmovies.data.local.repository.AllMoviesRemoteDataSource
import ru.yundon.allmovies.utils.Constants.FAVORITES_TABLE
import ru.yundon.allmovies.utils.Constants.POPULAR_TABLE
import ru.yundon.allmovies.utils.Constants.UPCOMING_TABLE

class PosterMovieViewModel(application: Application): AndroidViewModel(application) {

    private val repositoryPoster = LocalAllMoviesRepository(application)
    private val remoteImage = AllMoviesRemoteDataSource(application)
    val liveDataImage = MutableLiveData<Bitmap?>()
    val liveDataText = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getImageAndText(table_name: String?, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val getMoviesInformation: AllMoviesListEntity = when(table_name){
                UPCOMING_TABLE -> repositoryPoster.getUpcomingMovieById(id)
                POPULAR_TABLE -> repositoryPoster.getPopularMovieById(id)
                FAVORITES_TABLE -> repositoryPoster.getFavoritesMovieById(id)
                else -> repositoryPoster.getSearchAllMoviesById(id)
            }

            _isLoading.postValue(true)
            liveDataText.postValue(getMoviesInformation.overview ?: "")
            liveDataImage.postValue(getMoviesInformation.posterPath?.let { remoteImage.getImage(it) })
            _isLoading.postValue(false)
        }
    }
}