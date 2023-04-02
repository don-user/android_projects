package ru.yundon.rss.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yundon.rss.data.api.dto.RssItemDto
import ru.yundon.rss.data.api.network.RssApi
import ru.yundon.rss.data.database.RssDao
import ru.yundon.rss.data.database.RssDbModel
import ru.yundon.rss.data.mapper.RssMapper
import ru.yundon.rss.domain.RssRepository
import ru.yundon.rss.domain.model.RssEntity
import ru.yundon.rss.utils.TypeOfNews
import javax.inject.Inject

class RssRepositoryImpl @Inject constructor(
    private val rssDao: RssDao,
    private val apiService: RssApi,
    private val mapper: RssMapper
): RssRepository {

    override suspend fun loadDataFromApi(newsName: String) : Boolean {
        val listRssItemDto: List<RssItemDto> = try {
            when(newsName){
                TypeOfNews.BREAKING_NEWS.newsName -> {
                    apiService.getBreakingNews().channelDto.itemDtoList
                }
                TypeOfNews.HARDWARE_NEWS.newsName -> {
                    apiService.getHardwareNews().channelDto.itemDtoList
                }
                TypeOfNews.GADGETS_NEWS.newsName -> {
                    apiService.getGadgetsNews().channelDto.itemDtoList
                }
                TypeOfNews.SOFTWARE_NEWS.newsName -> {
                    apiService.getSoftwareNews().channelDto.itemDtoList
                }
                TypeOfNews.GAMES_NEWS.newsName -> {
                    apiService.getGameNews().channelDto.itemDtoList
                }
                else -> emptyList()
            }
        }catch (e: Exception){
            emptyList()
        }
        Log.d("TAG", "loadDataFromApi $listRssItemDto")
        rssDao.insertRssNewsList(
            mapper.mapDtoListToDbModelList(listRssItemDto, newsName)
        )
        return listRssItemDto.isNotEmpty()
    }

    override fun getRssInfo(typeNews: String): Flow<List<RssEntity>> {
        val listRssDbModel = rssDao.getListRssNews(typeNews)
        return listRssDbModel.map {
            mapper.mapDbModelListToEntityList(it)
        }
    }

    override suspend fun isFavorites(item: RssEntity) {
        val itemDbModel = mapper.mapRssEntityToDbModel(item)
        Log.d("TAG", "RssRepositoryImpl isFavorites ${itemDbModel.isFavorites}")
        val newItemDbModel: RssDbModel =
            if (itemDbModel.isFavorites) itemDbModel.copy(isFavorites = false)
            else itemDbModel.copy(isFavorites = true)
        Log.d("TAG", "RssRepositoryImpl isFavorites ${newItemDbModel.isFavorites}")
        rssDao.updateRssNewsItem(newItemDbModel)
    }

    override fun getFavoritesList(): Flow<List<RssEntity>> {
        val favoritesListDbModel = rssDao.getFavoritesList(true)
        return favoritesListDbModel.map {
            mapper.mapDbModelListToEntityList(it)
        }
    }
}