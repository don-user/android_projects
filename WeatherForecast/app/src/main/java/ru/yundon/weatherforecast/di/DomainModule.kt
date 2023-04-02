package ru.yundon.weatherforecast.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.yundon.weatherforecast.domain.CitiesWeatherRepository
import ru.yundon.weatherforecast.domain.usecases.DataRequestCitiesWeatherUseCase
import ru.yundon.weatherforecast.domain.usecases.GetCitiesWeatherListUseCase
import ru.yundon.weatherforecast.domain.usecases.GetCityWeatherByNameUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetCitiesWeatherListUseCase(
        repository: CitiesWeatherRepository
    ): GetCitiesWeatherListUseCase {
        return GetCitiesWeatherListUseCase(citiesWeatherRepository = repository)
    }

    @Provides
    fun provideGetCityWeatherByNameUseCase(
        repository: CitiesWeatherRepository
    ): GetCityWeatherByNameUseCase {
        return GetCityWeatherByNameUseCase(citiesWeatherRepository = repository)
    }


    @Provides
    fun provideDataRequestCitiesWeatherUseCase(
        repository: CitiesWeatherRepository
    ): DataRequestCitiesWeatherUseCase {
        return DataRequestCitiesWeatherUseCase(citiesWeatherRepository = repository)
    }

}