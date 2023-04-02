package ru.yundon.weatherforecast.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yundon.weatherforecast.data.api.apimodel.CitiesWeatherInfo
import ru.yundon.weatherforecast.utils.Constants.ADDITIONAL_PART_URL
import ru.yundon.weatherforecast.utils.Constants.CITY

interface ApiCitiesWeather {

    @GET(ADDITIONAL_PART_URL)
    suspend fun getCitiesWeatherInfo(@Query(CITY) city: String): Response<CitiesWeatherInfo>
}