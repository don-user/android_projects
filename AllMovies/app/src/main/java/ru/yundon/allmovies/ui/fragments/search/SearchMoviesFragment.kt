package ru.yundon.allmovies.ui.fragments.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.yundon.allmovies.adapter.AllMoviesAdapter
import ru.yundon.allmovies.databinding.FragmentSearchMoviesBinding
import ru.yundon.allmovies.ui.postermovie.PosterMovieActivity
import ru.yundon.allmovies.utils.Constants
import ru.yundon.allmovies.utils.Constants.MESSAGE_IS_FAVORITES
import ru.yundon.allmovies.utils.Constants.MESSAGE_IS_NOT_FAVORITES
import ru.yundon.allmovies.utils.Constants.MESSAGE_SEARCH_NEXT
import ru.yundon.allmovies.utils.Constants.SEARCH_TABLE
import ru.yundon.allmovies.utils.showSnackbar


class SearchMoviesFragment : Fragment(), AllMoviesAdapter.ItemClickListener {

    private var fragmentSearchAllMovies: FragmentSearchMoviesBinding? = null
    private lateinit var binding: FragmentSearchMoviesBinding
    private var adapterSearch: AllMoviesAdapter = AllMoviesAdapter(this)
    private lateinit var searchAllMovieViewModel: ViewModelSearchAllMovies

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        fragmentSearchAllMovies = binding

        searchAllMovieViewModel = ViewModelProvider(this, SearchViewModelFactory(activity!!.application))[ViewModelSearchAllMovies::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar()
        setupRecyclerView()
        initSearchAllMovies()
    }

    override fun onItemClick(title: String?, id: Int) {
        requireActivity().run {
            startActivity(
                Intent(this, PosterMovieActivity::class.java)
                .apply {
                    putExtra(Constants.EXTRA, SEARCH_TABLE)
                    putExtra(Constants.EXTRA_ID, id)
                    putExtra(Constants.EXTRA_TITLE, title)
                })
        }
    }

    override fun onFavoriteClick(id: Int) {

        searchAllMovieViewModel.apply {
            updateFavoritesStatusSearch(id)
            favorites.observe(this@SearchMoviesFragment) {
                if (it) showSnackbar(binding.root, MESSAGE_IS_FAVORITES)
                else showSnackbar(binding.root, MESSAGE_IS_NOT_FAVORITES)
            }
        }
    }

    override fun onDestroy() {
        searchAllMovieViewModel.deleteSearchTable()
        fragmentSearchAllMovies = null
        super.onDestroy()
    }

    private fun setupRecyclerView(){
        binding.searchMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterSearch
            setHasFixedSize(true)
        }
    }

    private fun initSearchAllMovies() {

        binding.buttonSearchMovies.setOnClickListener { view ->

            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            searchAllMovieViewModel.deleteSearchTable()
            showSnackbar(view, MESSAGE_SEARCH_NEXT)

            searchAllMovieViewModel.searchAllMovie(binding.searchView.text.toString())
            searchAllMovieViewModel.liveDataSearchAllMovies.observe(this@SearchMoviesFragment, {
                        adapterSearch.updateList(it)
                    })
        }

    }

    private fun progressBar(){
        searchAllMovieViewModel.isLoading.observe(this){
            binding.progressBarLoadingSearch.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}