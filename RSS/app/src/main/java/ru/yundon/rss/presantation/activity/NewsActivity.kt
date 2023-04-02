package ru.yundon.rss.presantation.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.yundon.rss.R
import ru.yundon.rss.databinding.ActivityNewsBinding
import ru.yundon.rss.utils.Constants.EMPTY_INTENT
import ru.yundon.rss.utils.Constants.EXTRA
import ru.yundon.rss.utils.Constants.KEY_ARGS

class NewsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNewsBinding.inflate(layoutInflater)
    }
    private val navHostController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavHostFragment)
                as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val newsName = intent.getStringExtra(EXTRA) ?: throw RuntimeException(EMPTY_INTENT)

        setBottomNavigationController(newsName)
        setToolbar(newsName)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setBottomNavigationController(newsName: String){
        val bundle = Bundle().apply {
            putString(KEY_ARGS, newsName)
        }
        navHostController.navigate(R.id.news, bundle)
        binding.bottomNavigation.setupWithNavController(navHostController)
    }

    private fun setToolbar(titleToolbar: String?){
        setSupportActionBar(binding.toolbarRss)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = titleToolbar
    }

    companion object{
        fun newIntentNews(context: Context, newsName: String) : Intent{
            return Intent(context, NewsActivity::class.java).apply {
                putExtra(EXTRA, newsName)
            }
        }
    }
}