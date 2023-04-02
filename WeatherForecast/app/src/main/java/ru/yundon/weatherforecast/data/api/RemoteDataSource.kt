package ru.yundon.weatherforecast.data.api

import retrofit2.Response
import ru.yundon.weatherforecast.data.api.apimodel.CitiesWeatherInfo
import javax.inject.Inject

class RemoteDataSource @Inject constructor( private val service: ApiCitiesWeather) {

    suspend fun getApiCitiesWeatherResult(city: String): CitiesWeatherInfo?{
        val response: Response<CitiesWeatherInfo>
        return try {
            response = service.getCitiesWeatherInfo(city)
            response.body()

        } catch (e: Exception) {
            null
        }
    }
}