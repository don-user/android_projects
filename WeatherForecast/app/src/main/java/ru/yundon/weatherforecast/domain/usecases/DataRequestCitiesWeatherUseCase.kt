package ru.yundon.weatherforecast.domain.usecases

import ru.yundon.weatherforecast.domain.CitiesWeatherRepository
import ru.yundon.weatherforecast.utils.Cities
import javax.inject.Inject

class DataRequestCitiesWeatherUseCase @Inject constructor(
    private val citiesWeatherRepository: CitiesWeatherRepository
    ){

    suspend fun requestCitiesWeather(): Boolean {
        var result = true
        for (item in Cities.values()) {

            if (!citiesWeatherRepository.requestCitiesWeather(item.city)) result = false
        }
        return result
    }
}