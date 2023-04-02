package ru.yundon.rss.presantation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.yundon.rss.databinding.FragmentFavouritesBinding
import ru.yundon.rss.presantation.RssNewsApp
import ru.yundon.rss.presantation.adapter.RssAdapter
import ru.yundon.rss.presantation.viewmodel.ViewModelFactory
import ru.yundon.rss.presantation.viewmodel.ViewModelRssNews
import ru.yundon.rss.utils.ChromeCustomTabHelper
import ru.yundon.rss.utils.Constants.EXCEPTION_MESSAGE_PARAM
import ru.yundon.rss.utils.Constants.MESSAGE_IS_NOT_FAVORITES
import ru.yundon.rss.utils.MakeToast
import javax.inject.Inject

class FragmentFavourites: Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding: FragmentFavouritesBinding
        get() = _binding ?: throw RuntimeException(EXCEPTION_MESSAGE_PARAM)

    private var adapterRss = RssAdapter()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelRss by lazy {
        ViewModelProvider(this, viewModelFactory)[ViewModelRssNews::class.java]
    }

    private val component by lazy {
        (requireActivity().application as RssNewsApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        observeListRss()
        setupRecyclerView()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView(){
        binding.rssFavoritesRecycler.apply {
            adapter = adapterRss
        }

        adapterRss.apply {
            itemFavoritesListener = {
                viewModelRss.setFavoritesStatus(it)
                MakeToast.toast(requireContext(), MESSAGE_IS_NOT_FAVORITES)
            }
            itemClickListener = {
                ChromeCustomTabHelper.openCct(requireContext(), it.link)
            }
        }
    }

    private fun observeListRss() = with(viewModelRss){
        favorites.observe(viewLifecycleOwner){
            adapterRss.submitList(it)
        }
    }
}