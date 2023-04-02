package ru.yundon.weatherforecast.domain.model

data class CityWeatherItem(
    var name: String,
    var temp: Double?,
    val feelsLike: Double?,
    val humidity: Int?,
    val seaLevel: Int?,
    val grndLevel: Int?,
    val windSpeed: Double?,
    val windDeg: Int?,
    val icon: String?,
    val description: String?,
)
