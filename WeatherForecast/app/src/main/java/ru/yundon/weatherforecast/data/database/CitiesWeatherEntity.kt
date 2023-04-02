package ru.yundon.weatherforecast.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_citiesWeather")
data class CitiesWeatherEntity (

    @PrimaryKey val name: String,
    val temp: Double?,
    val feelsLike: Double?,
    val humidity: Int?,
    val seaLevel: Int?,
    val grndLevel: Int?,
    val windSpeed: Double?,
    val windDeg: Int?,
    val description: String?,
    var icon: String?
)