package ru.yundon.allmovies.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_allMovies")
data class AllMoviesListEntity (
        @PrimaryKey val id : Int = 0,
        @ColumnInfo(name = "posterPath") val posterPath: String? = null,
        @ColumnInfo(name = "overview") var overview: String?,
        @ColumnInfo(name = "releaseDate") var releaseDate: String?,
        @ColumnInfo(name = "originalTitle") var originalTitle: String?,
        @ColumnInfo(name = "originalLanguage") var originalLanguage: String?,
        @ColumnInfo(name = "title") var title: String?,
        @ColumnInfo(name = "backdropPath") var backdropPath: String? = null,
        @ColumnInfo(name = "popularity") var popularity: Double?,
        @ColumnInfo(name = "voteCount") var voteCount: Int?,
        @ColumnInfo(name = "voteAverage") var voteAverage: Double?,
        @ColumnInfo(name = "totalPages") var totalPages: Int,
        @ColumnInfo(name = "isFavorite") var isFavourite: Boolean = false,
        @ColumnInfo(name = "page") var page: Int
        )