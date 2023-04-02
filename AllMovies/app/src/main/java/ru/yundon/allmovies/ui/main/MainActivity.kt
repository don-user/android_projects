package ru.yundon.allmovies.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.yundon.allmovies.R
import ru.yundon.allmovies.databinding.ActivityMainBinding
import ru.yundon.allmovies.BuildConfig

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            navController = (supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment).navController
            menuBottomNavView.setupWithNavController(navController)
        }

    }

    companion object{
        const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}