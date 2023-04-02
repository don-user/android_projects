package ru.yundon.rss.di

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.yundon.rss.data.api.network.RssApi
import ru.yundon.rss.data.api.network.RssApiClient
import ru.yundon.rss.data.database.RssDao
import ru.yundon.rss.data.database.RssDatabase

@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun provideRssDao(application: Application): RssDao{
        return RssDatabase.buildRssDatabase(application).rssDao()
    }

    @Provides
    @ApplicationScope
    fun provideRssApi(): RssApi{
        return RssApiClient.ApiRetrofit
    }

}