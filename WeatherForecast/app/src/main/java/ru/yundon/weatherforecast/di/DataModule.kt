package ru.yundon.weatherforecast.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.yundon.weatherforecast.data.CityWeatherMapper
import ru.yundon.weatherforecast.data.api.ApiCitiesWeather
import ru.yundon.weatherforecast.data.api.RemoteDataSource
import ru.yundon.weatherforecast.data.database.CitiesWeatherDatabase
import ru.yundon.weatherforecast.data.datarepository.CitiesWeatherLocalStorage
import ru.yundon.weatherforecast.data.datarepository.CitiesWeatherRemoteStorage
import ru.yundon.weatherforecast.data.datarepository.CitiesWeatherRepositoryImpl
import ru.yundon.weatherforecast.domain.CitiesWeatherRepository
import ru.yundon.weatherforecast.utils.Constants.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): ApiCitiesWeather {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiCitiesWeather::class.java)
    }

    @Provides
    @Singleton
    fun provideDataSource(service: ApiCitiesWeather) : RemoteDataSource{
        return RemoteDataSource(service)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : CitiesWeatherDatabase{
        return CitiesWeatherDatabase.buildDatabase(context)
    }

    @Provides
    @Singleton
    fun provideLocalStorage(database: CitiesWeatherDatabase) : CitiesWeatherLocalStorage{
        return CitiesWeatherLocalStorage(database)
    }

    @Provides
    @Singleton
    fun provideRemoveStorage(
        remoteDataSource: RemoteDataSource,
        mapper: CityWeatherMapper
    ) : CitiesWeatherRemoteStorage {
        return CitiesWeatherRemoteStorage(remoteDataSource, mapper)
    }

    @Provides
    @Singleton
    fun provideMapper() : CityWeatherMapper{
        return CityWeatherMapper()
    }

    @Provides
    @Singleton
    fun provideCitiesWeatherRepository(
        localStorage: CitiesWeatherLocalStorage,
        remoteStorage: CitiesWeatherRemoteStorage,
        mapper: CityWeatherMapper
    ): CitiesWeatherRepository {
        return CitiesWeatherRepositoryImpl(
            localStorage = localStorage,
            removeStorage = remoteStorage,
            mapper = mapper
        )
    }
}