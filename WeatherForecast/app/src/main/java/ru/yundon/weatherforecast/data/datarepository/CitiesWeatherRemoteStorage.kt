package ru.yundon.weatherforecast.data.datarepository

import ru.yundon.weatherforecast.data.CityWeatherMapper
import ru.yundon.weatherforecast.data.api.RemoteDataSource
import ru.yundon.weatherforecast.data.database.CitiesWeatherEntity
import javax.inject.Inject

class CitiesWeatherRemoteStorage @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val mapper: CityWeatherMapper
    ) {

    suspend fun getCityWeatherInfo(city: String): CitiesWeatherEntity? {
        val cityWeather = remoteDataSource.getApiCitiesWeatherResult(city)
        return if (cityWeather != null) mapper.mapJsonToDataModule(cityWeather)
        else null
    }


}