package ru.yundon.rss.domain.usecases

import ru.yundon.rss.domain.RssRepository
import ru.yundon.rss.domain.model.RssEntity
import javax.inject.Inject

class IsFavoritesUseCase @Inject constructor (private val repository: RssRepository) {

    suspend fun isFavorites (item: RssEntity){
        repository.isFavorites(item)
    }

    fun getFavoritesList() = repository.getFavoritesList()

}