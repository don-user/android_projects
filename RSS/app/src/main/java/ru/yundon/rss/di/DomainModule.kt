package ru.yundon.rss.di

import dagger.Binds
import dagger.Module
import ru.yundon.rss.data.repository.RssRepositoryImpl
import ru.yundon.rss.domain.RssRepository

@Module
interface DomainModule {

    @Binds
    @ApplicationScope
    fun bindRssRepository(impl: RssRepositoryImpl): RssRepository
}