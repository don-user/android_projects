package ru.yundon.rss.presantation

import android.app.Application
import ru.yundon.rss.di.DaggerApplicationComponent

class RssNewsApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}