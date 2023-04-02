package ru.yundon.allmovies.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.yundon.allmovies.utils.Constants.FAVORITES_TABLE
import ru.yundon.allmovies.utils.Constants.POPULAR_TABLE
import ru.yundon.allmovies.utils.Constants.SEARCH_TABLE
import ru.yundon.allmovies.utils.Constants.UPCOMING_TABLE

@Database(entities = [AllMoviesListEntity::class], version = 1, exportSchema = false)
abstract class AllMoviesDatabase: RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object{
        @Volatile
        private var TABLE_POPULAR: AllMoviesDatabase? = null
        private var TABLE_UPCOMING: AllMoviesDatabase? = null
        private var TABLE_SEARCH: AllMoviesDatabase? = null

        fun buildPopularDatabase(context: Context) : AllMoviesDatabase {
            val tempTable = TABLE_POPULAR
            if (tempTable != null){
                return tempTable
            }
            synchronized(this){
                val tablePopularAllMovies = Room.databaseBuilder(context, AllMoviesDatabase::class.java, POPULAR_TABLE).build()
                TABLE_POPULAR = tablePopularAllMovies
                return tablePopularAllMovies
            }
        }
        fun buildDatabaseUpcomingMovies(context: Context) : AllMoviesDatabase {
            val tempTable = TABLE_UPCOMING
            if (tempTable != null){
                return tempTable
            }
            synchronized(this){
                val tableUpcomingAllMovies = Room.databaseBuilder(context, AllMoviesDatabase::class.java, UPCOMING_TABLE).build()
                TABLE_UPCOMING = tableUpcomingAllMovies
                return tableUpcomingAllMovies
            }
        }
        fun buildFavoritesDatabase(context: Context) : AllMoviesDatabase {
            return Room.databaseBuilder(context, AllMoviesDatabase::class.java, FAVORITES_TABLE).build()
        }

        fun buildSearchDatabase(context: Context) : AllMoviesDatabase {
            val tempTable = TABLE_SEARCH
            if (tempTable != null){
                return tempTable
            }
            synchronized(this){
                val tableSearchAllMovies = Room.databaseBuilder(context, AllMoviesDatabase::class.java, SEARCH_TABLE).build()
                TABLE_SEARCH = tableSearchAllMovies
                return tableSearchAllMovies
            }
        }
    }
}