package ru.yundon.weatherforecast.data

import ru.yundon.weatherforecast.data.api.apimodel.CitiesWeatherInfo
import ru.yundon.weatherforecast.data.database.CitiesWeatherEntity
import ru.yundon.weatherforecast.domain.model.CityWeatherItem

class CityWeatherMapper {

    fun mapListToDomain(listCitiesWeather: List<CitiesWeatherEntity>) : List <CityWeatherItem>{
        return listCitiesWeather.map {
            mapItemToDomain(it)
        }
    }


    fun mapItemToDomain(item: CitiesWeatherEntity): CityWeatherItem {
        return CityWeatherItem(
            name = item.name,
            temp = item.temp,
            feelsLike = item.feelsLike,
            humidity = item.humidity,
            seaLevel = item.seaLevel,
            grndLevel = item.grndLevel,
            windSpeed = item.windSpeed,
            windDeg = item.windDeg,
            icon = item.icon,
            description = item.description
        )
    }

    fun mapJsonToDataModule(json: CitiesWeatherInfo): CitiesWeatherEntity {

        return CitiesWeatherEntity(
            json.name,
            json.main?.temp,
            json.main?.feelsLike,
            json.main?.humidity,
            json.main?.seaLevel,
            json.main?.grndLevel,
            json.wind?.speed,
            json.wind?.deg,
            json.weather[0].description,
            json.weather[0].icon,
        )
    }
}