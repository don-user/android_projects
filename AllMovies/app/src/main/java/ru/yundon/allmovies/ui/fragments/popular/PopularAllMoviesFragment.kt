package ru.yundon.allmovies.ui.fragments.popular

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
import ru.yundon.allmovies.databinding.FragmentPopularMoviesBinding
import ru.yundon.allmovies.ui.postermovie.PosterMovieActivity
import ru.yundon.allmovies.utils.Constants.EXTRA
import ru.yundon.allmovies.utils.Constants.EXTRA_ID
import ru.yundon.allmovies.utils.Constants.EXTRA_TITLE
import ru.yundon.allmovies.utils.Constants.MESSAGE_DOWNLOAD_NEXT
import ru.yundon.allmovies.utils.Constants.MESSAGE_IS_FAVORITES
import ru.yundon.allmovies.utils.Constants.MESSAGE_IS_NOT_FAVORITES
import ru.yundon.allmovies.utils.Constants.POPULAR_TABLE
import ru.yundon.allmovies.utils.showSnackbar


class PopularAllMoviesFragment : Fragment(), AllMoviesAdapter.ItemClickListener {

    private var fragmentPopularMovies: FragmentPopularMoviesBinding? = null
    private lateinit var binding: FragmentPopularMoviesBinding
    private var adapterPopular: AllMoviesAdapter = AllMoviesAdapter(this)
    private lateinit var popularMoviesViewModel: ViewModelPopularAllMovies
    private var click = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        fragmentPopularMovies = binding

        popularMoviesViewModel = ViewModelProvider(this, PopularViewModelFactory(activity!!.application))[ViewModelPopularAllMovies::class.java]
        observePopularData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            progressBar()
            setupRecyclerView()
            textClick()
            initSearchPopularMovie()
            addNewItem()
    }

    override fun onItemClick(title: String?, id: Int) {
        requireActivity().run {
            startActivity(Intent(this, PosterMovieActivity::class.java)
                .apply {
                    putExtra(EXTRA, POPULAR_TABLE)
                    putExtra(EXTRA_ID, id)
                    putExtra(EXTRA_TITLE, title)
                })
        }
    }

    override fun onFavoriteClick(id: Int) {
        popularMoviesViewModel.apply {
            updateFavoritesStatusPopular(id)
            favorites.observe(this@PopularAllMoviesFragment){
                if (it) showSnackbar(binding.root, MESSAGE_IS_FAVORITES)
                else showSnackbar(binding.root, MESSAGE_IS_NOT_FAVORITES)
            }
        }
    }

    override fun onDestroy() {
        fragmentPopularMovies = null
        super.onDestroy()
    }

    private fun setupRecyclerView(){
        binding.popularMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterPopular
            setHasFixedSize(true)
        }
    }

    private fun initSearchPopularMovie(){
        binding.searchViewPopular.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) searchPopularMovie(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) searchPopularMovie(p0)
                return true
            }
        })
    }

    private fun observePopularData(){
        popularMoviesViewModel.listPopularMovies.observe(this){
            adapterPopular.updateList(it)
        }
    }

    private fun searchPopularMovie(query: String){
        val search = "%$query%"
        popularMoviesViewModel.searchPopularMovie(search).observe(this, {
            adapterPopular.updateList(it)
        })
    }

    private fun addNewItem(){
        binding.popularMoviesRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy > 0 && !click) {
                    showSnackbar(binding.root, MESSAGE_DOWNLOAD_NEXT)
                    popularMoviesViewModel.nextPagePopularMovies()
                }
            }
        })
    }

    private fun textClick() = with(binding) {
        tViewSearchPopular.setOnClickListener {
            setVisibleSearch(click)
            click = !click
        }
    }

    private fun setVisibleSearch(click: Boolean) = with(binding){
        if (!click){
            searchViewPopular.visibility = View.VISIBLE
        } else {
            searchViewPopular.visibility = View.GONE
            searchViewPopular.setQuery("", false)
        }
    }

    private fun progressBar(){
        popularMoviesViewModel.isLoading.observe(this){
            binding.progressBarLoadingPopular.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}