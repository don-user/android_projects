package ru.yundon.allmovies.ui.fragments.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.yundon.allmovies.adapter.AllMoviesAdapter
import ru.yundon.allmovies.databinding.FragmentFavoritesMoviesBinding
import ru.yundon.allmovies.ui.postermovie.PosterMovieActivity
import ru.yundon.allmovies.utils.Constants.EXTRA
import ru.yundon.allmovies.utils.Constants.EXTRA_ID
import ru.yundon.allmovies.utils.Constants.EXTRA_TITLE
import ru.yundon.allmovies.utils.Constants.FAVORITES_TABLE
import ru.yundon.allmovies.utils.Constants.MESSAGE_IS_FAVORITES
import ru.yundon.allmovies.utils.Constants.MESSAGE_IS_NOT_FAVORITES
import ru.yundon.allmovies.utils.showSnackbar

class FavoritesAllMoviesFragment: Fragment(), AllMoviesAdapter.ItemClickListener {

    private var fragmentFavoritesMoviesBinding: FragmentFavoritesMoviesBinding? = null
    lateinit var binding: FragmentFavoritesMoviesBinding
    private val adapterFavorites: AllMoviesAdapter = AllMoviesAdapter(this)
    private lateinit var favoritesMoviesViewModel: ViewModelFavoritesAllMovies

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritesMoviesBinding.inflate(inflater, container, false)
        fragmentFavoritesMoviesBinding = binding

        favoritesMoviesViewModel = ViewModelProvider(this, FavoritesViewModelFactory(activity!!.application))[ViewModelFavoritesAllMovies::class.java]
        observeUpcomingData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            setupRecyclerView()
    }

    override fun onItemClick(title: String?, id: Int) {
        requireActivity().run {
            startActivity(Intent(this, PosterMovieActivity::class.java)
                .apply {
                    putExtra(EXTRA, FAVORITES_TABLE)
                    putExtra(EXTRA_ID, id)
                    putExtra(EXTRA_TITLE, title)
                })
        }
    }

    override fun onFavoriteClick(id: Int) {
        favoritesMoviesViewModel.updateFavoritesStatus(id)
        favoritesMoviesViewModel.favoritesStatus(id)
        favoritesMoviesViewModel.favorites.observe(this){
            if (!it) showSnackbar(binding.root, MESSAGE_IS_FAVORITES)
            else showSnackbar(binding.root, MESSAGE_IS_NOT_FAVORITES)
        }
    }

    override fun onDestroy() {
        fragmentFavoritesMoviesBinding = null
        super.onDestroy()
    }

    private fun setupRecyclerView(){
        binding.favoritesMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterFavorites
            setHasFixedSize(true)
        }
    }

    private fun observeUpcomingData(){
        favoritesMoviesViewModel.listFavoritesMovies.observe(this){
            adapterFavorites.updateList(it)
        }
    }
}