package ru.yundon.allmovies.data.local.repository


interface IsFavoritesHelper {

    suspend fun isFavoritesAllMovieStatus(id: Int)

    suspend fun isFavoritesAllMoviePopular(id: Int)

    suspend fun isFavoritesAllMovieUpcoming(id: Int)

    suspend fun isFavoritesAllMovieSearch(id: Int)
}