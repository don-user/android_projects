package ru.yundon.weatherforecast.data.api.apimodel

import com.google.gson.annotations.SerializedName


data class Clouds(

    @SerializedName("all") var all: Int? = null

)
