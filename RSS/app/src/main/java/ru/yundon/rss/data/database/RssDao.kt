package ru.yundon.rss.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RssDao {

    @Query("SELECT * FROM rss_table WHERE typeNews == :typeNews")
    fun getListRssNews(typeNews: String): Flow<List<RssDbModel>>

    @Query("SELECT * FROM rss_table WHERE isFavorites == :favoritesStatus")
    fun getFavoritesList(favoritesStatus: Boolean): Flow<List<RssDbModel>>

    @Update
    suspend fun updateRssNewsItem(item: RssDbModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRssNewsList(list: List<RssDbModel>)

}