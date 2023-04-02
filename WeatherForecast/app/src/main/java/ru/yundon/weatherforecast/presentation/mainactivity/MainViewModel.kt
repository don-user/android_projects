package ru.yundon.weatherforecast.presentation.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yundon.weatherforecast.domain.usecases.DataRequestCitiesWeatherUseCase
import ru.yundon.weatherforecast.domain.usecases.GetCitiesWeatherListUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getCitiesWeatherListUseCase: GetCitiesWeatherListUseCase,
    private val requestCitiesWeatherUseCase: DataRequestCitiesWeatherUseCase
): ViewModel() {

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        requestCitiesWeatherInfo()
    }

    val citiesWeatherList = getCitiesWeatherListUseCase.getCitiesWeatherList()

    private fun requestCitiesWeatherInfo(){

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            _error.postValue(requestCitiesWeatherUseCase.requestCitiesWeather())
            _isLoading.postValue(false)
        }

    }
}