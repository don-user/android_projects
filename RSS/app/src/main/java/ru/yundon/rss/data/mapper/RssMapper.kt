package ru.yundon.rss.data.mapper

import ru.yundon.rss.data.api.dto.RssItemDto
import ru.yundon.rss.data.database.RssDbModel
import ru.yundon.rss.domain.model.RssEntity
import javax.inject.Inject

class RssMapper @Inject constructor() {

    fun mapDtoListToDbModelList(dtoList: List<RssItemDto>, newsName: String) : List<RssDbModel>{
        return dtoList.map {
            RssDbModel(
                title = it.title,
                typeNews = newsName,
                link = it.link,
                description = it.description,
                pubDate = it.pubDate,
                imageUrl = it.enclosure.url,
                isFavorites = false
            )
        }
    }

    fun mapDbModelListToEntityList(dbModel: List<RssDbModel>): List<RssEntity>{
        return dbModel.map {
            RssEntity (
                title = it.title,
                typeNews = it.typeNews,
                link = it.link,
                description = it.description,
                pubDate = it.pubDate,
                imageUrl = it.imageUrl,
                isFavorites = it.isFavorites
            )
        }
    }

    fun mapRssEntityToDbModel(entity: RssEntity) = RssDbModel(
        title = entity.title,
        typeNews = entity.typeNews,
        link = entity.link,
        description = entity.description,
        pubDate = entity.pubDate,
        imageUrl = entity.imageUrl,
        isFavorites = entity.isFavorites
    )
}