package ru.yundon.weatherforecast.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesWeatherDao {

    @Query("SELECT * FROM table_citiesWeather ORDER BY name")
    fun getListCitiesWeather(): Flow<List<CitiesWeatherEntity>>

    @Query("SELECT * FROM table_citiesWeather WHERE name = :name")
    suspend fun getCityWeatherByName(name: String): CitiesWeatherEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityWeather (item: CitiesWeatherEntity)
}