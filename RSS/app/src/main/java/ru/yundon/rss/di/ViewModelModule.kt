package ru.yundon.rss.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.yundon.rss.presantation.viewmodel.ViewModelRssNews

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelRssNews::class)
    fun bindViewModelRssNews(viewModel: ViewModelRssNews): ViewModel
}