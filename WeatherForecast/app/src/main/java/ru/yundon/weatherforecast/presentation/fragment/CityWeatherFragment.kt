package ru.yundon.weatherforecast.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.yundon.weatherforecast.databinding.FragmentCityWeatherBinding
import ru.yundon.weatherforecast.presentation.cityweatheractivity.CityWeatherViewModel
import ru.yundon.weatherforecast.utils.*
import ru.yundon.weatherforecast.utils.Constants.ERROR
import ru.yundon.weatherforecast.utils.Constants.EXCEPTION_MESSAGE_BINDING
import ru.yundon.weatherforecast.utils.Constants.EXTRA_NAME


@AndroidEntryPoint
class CityWeatherFragment : Fragment() {

    private val viewModel: CityWeatherViewModel by viewModels()

    private var _binding: FragmentCityWeatherBinding? = null
    private val binding: FragmentCityWeatherBinding
        get() = _binding ?: throw RuntimeException (EXCEPTION_MESSAGE_BINDING)
    private var cityName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) getCityName()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityWeatherBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) getCityWeatherInfo(cityName)

        observeCityWeatherItem()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getCityName(){

        val args = arguments
        if (args == null || !args.containsKey(EXTRA_NAME))
            throw RuntimeException(Constants.EXCEPTION_MESSAGE_PARAM)
        cityName = args.getString(EXTRA_NAME)

    }

    private fun getCityWeatherInfo(city: String?) = with(binding) {

        if (city != null) viewModel.getCityWeatherItemByName(city)
        else showSnackBar(binding.root, ERROR)
    }

    private fun observeCityWeatherItem() = with(binding){
        viewModel.cityWeatherItem.observe(viewLifecycleOwner){
            tvHumidityValue.text = it.humidity.percentHelper()
            tvTempFeelsLikeValue.text = it.feelsLike.tempHelper()
            tvWindSpeedValue.text = it.windSpeed.msHelper()
            tvWindDegValue.text = it.windDeg.degreeHelper()
            tvSeaLevelValue.text = it.seaLevel.gpaHelper()
            tvGrndLevelValue.text = it.grndLevel.gpaHelper()
        }
    }

    companion object {

        fun newInstanceCityByName(cityWeatherName: String?) :CityWeatherFragment{
            return CityWeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_NAME, cityWeatherName)
                }
            }
        }
    }
}