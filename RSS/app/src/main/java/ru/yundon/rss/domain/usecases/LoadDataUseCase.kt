package ru.yundon.rss.domain.usecases

import ru.yundon.rss.domain.RssRepository
import javax.inject.Inject


class LoadDataUseCase @Inject constructor (private val repository: RssRepository){

    suspend operator fun invoke(newsName: String) : Boolean {
        return repository.loadDataFromApi(newsName)
    }
}