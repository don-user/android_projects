package ru.yundon.weatherforecast.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.yundon.weatherforecast.domain.model.CityWeatherItem

class CityItemDiffCallback : DiffUtil.ItemCallback<CityWeatherItem>() {

    override fun areItemsTheSame(oldItem: CityWeatherItem, newItem: CityWeatherItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CityWeatherItem, newItem: CityWeatherItem): Boolean {
        return oldItem == newItem
    }

}
