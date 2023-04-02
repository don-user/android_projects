package ru.yundon.weatherforecast.data.datarepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import ru.yundon.weatherforecast.data.CityWeatherMapper
import ru.yundon.weatherforecast.domain.CitiesWeatherRepository
import ru.yundon.weatherforecast.domain.model.CityWeatherItem
import javax.inject.Inject

class CitiesWeatherRepositoryImpl @Inject constructor(
    private val localStorage: CitiesWeatherLocalStorage,
    private val removeStorage: CitiesWeatherRemoteStorage,
    private val mapper: CityWeatherMapper
) : CitiesWeatherRepository {


    override fun getCitiesWeatherList(): LiveData<List<CityWeatherItem>> = Transformations.map(
        localStorage.citiesWeatherList.asLiveData()
    ){
        mapper.mapListToDomain(it)
    }

    override suspend fun getCitiesWeatherItem(name: String): CityWeatherItem {

        val cityWeather = localStorage.getCityWeatherInfoByName(name)
        return mapper.mapItemToDomain(cityWeather)
    }

    override suspend fun requestCitiesWeather(city: String): Boolean {

        val cityWeatherInfo = removeStorage.getCityWeatherInfo(city)
        return if (cityWeatherInfo != null) {
            localStorage.insertCitiesIntoDatabase(cityWeatherInfo)
            false
        } else true
    }
}