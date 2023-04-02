package ru.yundon.weatherforecast.presentation.cityweatheractivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yundon.weatherforecast.domain.model.CityWeatherItem
import ru.yundon.weatherforecast.domain.usecases.GetCityWeatherByNameUseCase
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val getCityWeatherByNameUseCase: GetCityWeatherByNameUseCase,
    ): ViewModel() {

    private val _cityWeatherItem = MutableLiveData<CityWeatherItem>()
    val cityWeatherItem: LiveData<CityWeatherItem> = _cityWeatherItem


    fun getCityWeatherItemByName(city: String){
        viewModelScope.launch(Dispatchers.IO) {
            val itemCity = getCityWeatherByNameUseCase.getCitiesWeatherItemByName(city)
            _cityWeatherItem.postValue(itemCity)
        }
    }
}