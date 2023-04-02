package ru.yundon.weatherforecast.domain.usecases

import ru.yundon.weatherforecast.domain.CitiesWeatherRepository
import ru.yundon.weatherforecast.domain.model.CityWeatherItem
import javax.inject.Inject

class GetCityWeatherByNameUseCase @Inject constructor(
    private val citiesWeatherRepository: CitiesWeatherRepository
    ) {

    suspend fun getCitiesWeatherItemByName(name: String): CityWeatherItem{
        return citiesWeatherRepository.getCitiesWeatherItem(name)
    }
}