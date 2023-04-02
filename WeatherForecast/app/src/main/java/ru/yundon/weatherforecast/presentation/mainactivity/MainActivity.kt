package ru.yundon.weatherforecast.presentation.mainactivity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.yundon.weatherforecast.databinding.ActivityMainBinding
import ru.yundon.weatherforecast.presentation.adapter.CityWeatherAdapter
import ru.yundon.weatherforecast.presentation.cityweatheractivity.CityWeatherActivity
import ru.yundon.weatherforecast.utils.Constants
import ru.yundon.weatherforecast.utils.Constants.ERROR
import ru.yundon.weatherforecast.utils.Constants.EXTRA_NAME
import ru.yundon.weatherforecast.utils.showSnackBar

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapterCityWeather: CityWeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        observeCityWeatherList()
        setupClickListener()
        progressBar()

    }

    private fun setupRecyclerView() {

        adapterCityWeather = CityWeatherAdapter()
        binding.recyclerViewCity.adapter = adapterCityWeather
    }

    private fun observeCityWeatherList(){

        viewModel.error.observe(this){
            if (it) showSnackBar(binding.root, ERROR)
        }

        viewModel.citiesWeatherList.observe(this) {
            if (it.isNullOrEmpty()) binding.tvErrorMsg.visibility = View.VISIBLE
            else {
                adapterCityWeather.submitList(it)
                binding.tvErrorMsg.visibility = View.GONE
            }
        }
    }

    private fun setupClickListener(){
        adapterCityWeather.onCityItemClickListener = {


            val intent = CityWeatherActivity.intentCityWeatherItemByName(this, it.name)
            startActivity(intent)

        }
    }
    private fun progressBar(){
        viewModel.isLoading.observe(this){
            binding.progressBarLoading.visibility = if(it) View.VISIBLE else View.GONE
        }
    }
}