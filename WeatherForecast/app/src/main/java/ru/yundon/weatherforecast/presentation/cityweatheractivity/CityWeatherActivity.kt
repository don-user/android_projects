package ru.yundon.weatherforecast.presentation.cityweatheractivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.yundon.weatherforecast.R
import ru.yundon.weatherforecast.databinding.ActivityCityWeatherBinding
import ru.yundon.weatherforecast.presentation.fragment.CityWeatherFragment
import ru.yundon.weatherforecast.utils.*
import ru.yundon.weatherforecast.utils.Constants.ERROR
import ru.yundon.weatherforecast.utils.Constants.EXCEPTION_MESSAGE_PARAM
import ru.yundon.weatherforecast.utils.Constants.EXTRA_NAME
import ru.yundon.weatherforecast.utils.Constants.TOOLBAR_TITLE

@AndroidEntryPoint
class CityWeatherActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCityWeatherBinding.inflate(layoutInflater) }
    private val viewModel: CityWeatherViewModel by viewModels()
    private var cityName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getNameIntent()
        observeCityWeatherItem()

        if (savedInstanceState == null) {
            launchFragment(CityWeatherFragment.newInstanceCityByName(cityName))
            getCityWeatherInfo(cityName)
        }
        setToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        else return super.onOptionsItemSelected(item)

        return true
    }

    private fun getNameIntent(){
        if (!intent.hasExtra(EXTRA_NAME)) throw RuntimeException(EXCEPTION_MESSAGE_PARAM)
        cityName = intent.getStringExtra(EXTRA_NAME)

    }

    private fun getCityWeatherInfo(city: String?) {
        if (city != null) viewModel.getCityWeatherItemByName(city)
        else showSnackBar(binding.root, ERROR)
    }

    private fun observeCityWeatherItem() = with(binding){
        viewModel.cityWeatherItem.observe(this@CityWeatherActivity){
            tvCity.text = it.name
            tvDescription.text = it.description
            ImageLoader.loadImage(this.iViewIcon, it.icon)
            tvTempMain.text = it.temp.tempHelper()
        }
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.cityWeatherContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbarWeather)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = TOOLBAR_TITLE

    }

    companion object{

        fun intentCityWeatherItemByName(context: Context, cityWeatherName: String): Intent{
            val intent = Intent(context, CityWeatherActivity::class.java)
            intent.putExtra(EXTRA_NAME, cityWeatherName)
            return intent
        }
    }
}