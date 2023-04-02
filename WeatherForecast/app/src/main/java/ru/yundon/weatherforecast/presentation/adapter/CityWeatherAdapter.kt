package ru.yundon.weatherforecast.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.yundon.weatherforecast.databinding.ItemCityBinding
import ru.yundon.weatherforecast.domain.model.CityWeatherItem

class CityWeatherAdapter: ListAdapter<CityWeatherItem, CityWeatherItemViewHolder>(
    CityItemDiffCallback()
) {

    private lateinit var binding: ItemCityBinding
    var onCityItemClickListener: ((CityWeatherItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityWeatherItemViewHolder {
        binding = ItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityWeatherItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityWeatherItemViewHolder, position: Int) {

        val cityItem = getItem(position)

        holder.bind(cityItem)

        holder.binding.root.setOnClickListener {
            onCityItemClickListener?.invoke(cityItem)
        }
    }
}

