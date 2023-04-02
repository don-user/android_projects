package ru.yundon.rss.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.yundon.rss.presantation.fragments.FragmentFavourites
import ru.yundon.rss.presantation.fragments.FragmentNews

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: FragmentNews)

    fun inject(fragment: FragmentFavourites)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}