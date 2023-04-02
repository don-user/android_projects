package ru.yundon.weatherforecast.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.yundon.weatherforecast.databinding.ItemCityBinding
import ru.yundon.weatherforecast.domain.model.CityWeatherItem
import ru.yundon.weatherforecast.utils.ImageLoader
import ru.yundon.weatherforecast.utils.tempHelper

class CityWeatherItemViewHolder (val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(cityItem: CityWeatherItem) = with(binding)  {

        tvCity.text = cityItem.name
        tvCount.text = cityItem.temp.tempHelper()
        ImageLoader.loadImage(ivIcon, cityItem.icon)

    }

}
