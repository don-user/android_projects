package ru.yundon.rss.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rss_table")
data class RssDbModel(
    @PrimaryKey
    var title: String,
    val typeNews: String,
    val link: String,
    val description: String,
    val pubDate: String,
    @ColumnInfo(name = "url")var imageUrl: String,
    var isFavorites: Boolean = false
)
