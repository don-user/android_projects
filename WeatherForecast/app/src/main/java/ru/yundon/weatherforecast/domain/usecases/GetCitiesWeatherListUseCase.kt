package ru.yundon.weatherforecast.domain.usecases

import androidx.lifecycle.LiveData
import ru.yundon.weatherforecast.domain.CitiesWeatherRepository
import ru.yundon.weatherforecast.domain.model.CityWeatherItem
import javax.inject.Inject

class GetCitiesWeatherListUseCase @Inject constructor(
    private val citiesWeatherRepository: CitiesWeatherRepository
    ){

    fun getCitiesWeatherList(): LiveData<List<CityWeatherItem>> {

        return citiesWeatherRepository.getCitiesWeatherList()
    }

}