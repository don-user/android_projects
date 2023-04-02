package ru.yundon.allmovies.api.model

import com.google.gson.annotations.SerializedName

data class MoviesData (
    var page: String,
    var results: List<MoviesInfo>,
    @SerializedName("total_results")
    var totalResults: String,
    @SerializedName("total_pages")
    var totalPages: String
)
