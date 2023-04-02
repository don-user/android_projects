package ru.yundon.allmovies.ui.postermovie

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.yundon.allmovies.R
import ru.yundon.allmovies.databinding.ActivityPosterMovieBinding
import ru.yundon.allmovies.utils.Constants.EXTRA
import ru.yundon.allmovies.utils.Constants.EXTRA_ID
import ru.yundon.allmovies.utils.Constants.EXTRA_TITLE
import ru.yundon.allmovies.utils.Constants.TEXT_NON
import ru.yundon.allmovies.utils.Constants.TITLE_TEXT

class PosterMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPosterMovieBinding
    private lateinit var posterMovieViewModel: PosterMovieViewModel
    private var click = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPosterMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 1)
        val tableName = intent.getStringExtra(EXTRA)
        val toolbarName = "\"${intent.getStringExtra(EXTRA_TITLE)}\""

        posterMovieViewModel= ViewModelProvider(this, PosterViewModelFactory(application))[PosterMovieViewModel::class.java]

        progressBar()
        findTable(tableName, id)
        observeData()
        imageAndTextClick()
        setToolbar(toolbarName)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setToolbar(titleToolbar: String){
        setSupportActionBar(binding.toolbarPoster)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = titleToolbar
    }

    private fun imageAndTextClick() = with(binding){
        iViewPoster.setOnClickListener {
            setVisibleImageFull(click)
            click = !click
        }

        iViewPosterFullScreen.setOnClickListener {
            setVisibleImageFull(click)
            click = !click
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeData(){
        posterMovieViewModel.liveDataText.observe(this) {
            with(binding) {
                if (it == "") tViewText.text = "$TITLE_TEXT $TEXT_NON"
                else tViewText.text = "$TITLE_TEXT $it"
            }
        }

        posterMovieViewModel.liveDataImage.observe(this, Observer {

            with(binding){
                if (it == null) {
                    iViewPoster.setImageResource(R.drawable.ic_launcher_foreground)
                    iViewPosterFullScreen.setImageResource(R.drawable.ic_launcher_foreground)
                }
                else {
                    iViewPoster.setImageBitmap(it)
                    iViewPosterFullScreen.setImageBitmap(it)
                }
            }
        })
    }

    private fun findTable(tableName: String?, id: Int){
            posterMovieViewModel.getImageAndText(tableName, id)
        }

    private fun setVisibleImageFull(click: Boolean) = with(binding){
        if (!click){
            scrollView.visibility = View.GONE
            toolbarPoster.visibility = View.GONE
            iViewPosterFullScreen.visibility = View.VISIBLE

        }else {
            scrollView.visibility = View.VISIBLE
            toolbarPoster.visibility = View.VISIBLE
            iViewPosterFullScreen.visibility = View.GONE
        }
    }

    private fun progressBar(){
        posterMovieViewModel.isLoading.observe(this){
            binding.progressBarLoadingPoster.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}
