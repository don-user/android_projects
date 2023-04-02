package ru.yundon.rss.presantation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.yundon.rss.domain.model.RssEntity
import ru.yundon.rss.domain.usecases.GetRssInfoUseCase
import ru.yundon.rss.domain.usecases.IsFavoritesUseCase
import ru.yundon.rss.domain.usecases.LoadDataUseCase
import javax.inject.Inject

class ViewModelRssNews @Inject constructor(
    private val getRssInfoUseCase: GetRssInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val isFavoritesUseCase: IsFavoritesUseCase
): ViewModel() {

    private var _getListRss = MutableLiveData<List<RssEntity>>()
    val getListRss: LiveData<List<RssEntity>>
        get() = _getListRss

    val favorites = isFavoritesUseCase.getFavoritesList().asLiveData()

    private var _errorConnection = MutableLiveData<Boolean>()
    val errorConnection: LiveData<Boolean>
        get() = _errorConnection

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadRssInfo(nameNews: String){
        _isLoading.value = true
        viewModelScope.launch {
            _errorConnection.postValue(loadDataUseCase.invoke(nameNews))
            _isLoading.value = false
        }

    }

    fun getListRssEntity(newsName: String){
        viewModelScope.launch() {
            _getListRss = getRssInfoUseCase.invoke(newsName) as MutableLiveData<List<RssEntity>>
        }
    }
    fun setFavoritesStatus(item: RssEntity){
        viewModelScope.launch {
            isFavoritesUseCase.isFavorites(item)
        }
    }
}