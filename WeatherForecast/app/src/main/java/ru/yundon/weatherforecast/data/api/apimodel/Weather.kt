package ru.yundon.weatherforecast.data.api.apimodel

import com.google.gson.annotations.SerializedName

data class Weather(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,

){
    @SerializedName("icon")
    var icon: String? = null
        get() = "http://openweathermap.org/img/wn/$field@2x.png"
}
