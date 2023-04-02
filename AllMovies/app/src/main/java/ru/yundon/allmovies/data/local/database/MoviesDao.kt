package ru.yundon.allmovies.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM table_allMovies")
    fun getListMovies(): Flow<List<AllMoviesListEntity>>

    @Query("SELECT * FROM table_allMovies")
    suspend fun getListAllMovies(): List<AllMoviesListEntity>

    @Query("SELECT * FROM table_allMovies WHERE id = :id")
    suspend fun getMovieById(id: Int): AllMoviesListEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(list: List<AllMoviesListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElement(model: AllMoviesListEntity)

    @Query("SELECT * FROM table_allMovies WHERE title LIKE :searchQuery")
    fun searchInDatabase(searchQuery: String): Flow<List<AllMoviesListEntity>>

    @Update
    suspend fun updateElement(model: AllMoviesListEntity)

    @Delete
    suspend fun deleteElement(model: AllMoviesListEntity)

    @Query("DELETE FROM table_allMovies")
    suspend fun deleteAll()

}