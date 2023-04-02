package ru.yundon.weatherforecast.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.yundon.weatherforecast.utils.Constants.DATABASE_VERSION

@Database(entities = [CitiesWeatherEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class CitiesWeatherDatabase: RoomDatabase() {

    abstract fun citiesWeatherDao(): CitiesWeatherDao

    companion object{
        @Volatile
        private var TABLE: CitiesWeatherDatabase? = null

        fun buildDatabase(context: Context) : CitiesWeatherDatabase{

            val tempTable = TABLE
            if (tempTable != null) return tempTable
            synchronized(this){
                val tableCitiesWeather = Room.databaseBuilder(
                    context,
                    CitiesWeatherDatabase::class.java,
                    "tableCitiesWeather"
                ).build()
                TABLE = tableCitiesWeather
                return tableCitiesWeather
            }
        }
    }
}