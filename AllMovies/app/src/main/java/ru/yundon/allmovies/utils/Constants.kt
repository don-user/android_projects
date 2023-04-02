package ru.yundon.allmovies.utils

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val LANGUAGE_RU = "ru"

    const val ERROR_CONNECTION = "ОТСУТВУЕТ ПОДКЛЮЧЕНИЕ К ИНТЕРНЕТУ"

    const val EXTRA_ID = "Extra ID"
    const val EXTRA_TITLE = "Extra title"
    const val EXTRA = "Extra"

    const val POPULAR_TABLE = "PopularAllMoviesDatabase"
    const val UPCOMING_TABLE = "UpcomingAllMoviesDatabase"
    const val FAVORITES_TABLE = "FavoritesAllMoviesDatabase"
    const val SEARCH_TABLE = "SearchAllMoviesDatabase"

    const val TITLE_TEXT = "КРАТКОЕ ОПИСАНИЕ ФИЛЬМА:\n"
    const val TEXT_NON = "К сожалению, описание фильма отсутвует."
    const val RELEASE_TEXT = "Дата релиза:"

    const val MESSAGE_IS_FAVORITES = "Фильм добавлен в избранное"
    const val MESSAGE_IS_NOT_FAVORITES = "Фильм удален из избранного"

    const val MESSAGE_DOWNLOAD_NEXT = "ЗАГРУЗКА СЛЕДУЮЩИХ ФИЛЬМОВ"
    const val MESSAGE_SEARCH_NEXT = "ПОИСК ФИЛЬМОВ ПО ЗАПРОСУ"

}