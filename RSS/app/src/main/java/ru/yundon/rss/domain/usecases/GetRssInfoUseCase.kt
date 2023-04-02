package ru.yundon.rss.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import ru.yundon.rss.domain.RssRepository
import ru.yundon.rss.domain.model.RssEntity
import javax.inject.Inject

class GetRssInfoUseCase @Inject constructor (private val repository: RssRepository) {

    operator fun invoke(typeNews: String): LiveData<List<RssEntity>> {
        return repository.getRssInfo(typeNews).asLiveData()
    }
}