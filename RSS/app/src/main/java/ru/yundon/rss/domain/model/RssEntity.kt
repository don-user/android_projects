package ru.yundon.rss.domain.model

data class RssEntity(
    val title: String,
    val typeNews: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val imageUrl: String,
    val isFavorites: Boolean = false
)
