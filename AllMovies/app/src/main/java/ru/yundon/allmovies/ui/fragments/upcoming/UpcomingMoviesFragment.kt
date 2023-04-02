package ru.yundon.allmovies.ui.fragments.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.yundon.allmovies.adapter.AllMoviesAdapter
import ru.yundon.allmovies.databinding.FragmentUpcomingMoviesBinding
import ru.yundon.allmovies.ui.postermovie.PosterMovieActivity
import ru.yundon.allmovies.utils.Constants
import ru.yundon.allmovies.utils.Constants.MESSAGE_DOWNLOAD_NEXT
import ru.yundon.allmovies.utils.Constants.MESSAGE_IS_FAVORITES
import ru.yundon.allmovies.utils.Constants.MESSAGE_IS_NOT_FAVORITES
import ru.yundon.allmovies.utils.showSnackbar

class UpcomingMoviesFragment: Fragment(), AllMoviesAdapter.ItemClickListener {

    private var fragmentNowPlayingMovies: FragmentUpcomingMoviesBinding? = null
    private var adapterUpcoming: AllMoviesAdapter = AllMoviesAdapter(this)
    private lateinit var binding: FragmentUpcomingMoviesBinding
    private lateinit var upcomingMoviesViewModel: ViewModelUpcomingMovies
    private var click = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUpcomingMoviesBinding.inflate(inflater, container, false)
        fragmentNowPlayingMovies = binding

        upcomingMoviesViewModel = ViewModelProvider(this, UpcomingViewModelFactory(activity!!.application))[ViewModelUpcomingMovies::class.java]

        observeUpcomingData()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar()
        setupRecyclerView()
        textClick()
        initSearchUpcomingMovie()
        addNewItem()
    }

    override fun onItemClick(title: String?, id: Int) {
        requireActivity().run {
            startActivity( Intent(this, PosterMovieActivity::class.java).apply {
                putExtra(Constants.EXTRA, Constants.UPCOMING_TABLE)
                putExtra(Constants.EXTRA_ID, id)
                putExtra(Constants.EXTRA_TITLE, title)
            })
        }
    }

    override fun onFavoriteClick(id: Int) {
        upcomingMoviesViewModel.apply {
            updateFavoritesStatusUpcoming(id)
            favorites.observe(this@UpcomingMoviesFragment){
                if (it) showSnackbar(binding.root, MESSAGE_IS_FAVORITES)
                else showSnackbar(binding.root, MESSAGE_IS_NOT_FAVORITES)
            }
        }
    }

    override fun onDestroy() {
        fragmentNowPlayingMovies = null
        super.onDestroy()
    }

    private fun setupRecyclerView(){
        binding.upcomingMoviesRecaycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterUpcoming
            setHasFixedSize(true)
        }
    }

    private fun initSearchUpcomingMovie(){
        binding.searchViewUpcoming.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) searchUpcomingMovie(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) searchUpcomingMovie(p0)
                return true
            }
        })
    }

    private fun observeUpcomingData(){
        upcomingMoviesViewModel.listUpcomingMovies.observe(this){
            adapterUpcoming.updateList(it)

        }
    }

    private fun searchUpcomingMovie(query: String?){
        val search = "%$query%"
        upcomingMoviesViewModel.searchUpcomingMovie(search).observe(this, {
            adapterUpcoming.updateList(it)
        })
    }

    private fun addNewItem() = with(binding){
        upcomingMoviesRecaycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy > 0 && !click){
                    showSnackbar(root, MESSAGE_DOWNLOAD_NEXT)
                    upcomingMoviesViewModel.nextPageUpcomingMovies()
                }
            }
        })
    }

    private fun textClick() = with(binding){
        tViewSearchUpcoming.setOnClickListener {
            setVisibleSearch(click)
            click = !click
        }
    }

    private fun setVisibleSearch(click: Boolean) = with(binding.searchViewUpcoming){
        if (!click) visibility = View.VISIBLE
        else {
            visibility = View.GONE
            setQuery("", false)
        }
    }

    //подключение прогрессбара к наблюдателю и отображения его и скрытие
    private fun progressBar(){
        upcomingMoviesViewModel.isLoading.observe(this){
            binding.progressBarLoadingUpcoming.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}