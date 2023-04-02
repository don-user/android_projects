package ru.yundon.allmovies.ui.fragments.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchViewModelFactory(private val application: Application): ViewModelProvider.AndroidViewModelFactory(application){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelSearchAllMovies(application) as T
    }
}