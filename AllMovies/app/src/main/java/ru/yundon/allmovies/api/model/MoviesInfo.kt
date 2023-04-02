package ru.yundon.allmovies.api.model

import com.google.gson.annotations.SerializedName

class MoviesInfo(
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("vote_count")
    var voteCount: Int?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
) {

    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
}
