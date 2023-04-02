package ru.yundon.rss.presantation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.yundon.rss.databinding.FragmentNewsBinding
import ru.yundon.rss.presantation.RssNewsApp
import ru.yundon.rss.presantation.adapter.RssAdapter
import ru.yundon.rss.presantation.viewmodel.ViewModelFactory
import ru.yundon.rss.presantation.viewmodel.ViewModelRssNews
import ru.yundon.rss.utils.ChromeCustomTabHelper
import ru.yundon.rss.utils.Constants.EXCEPTION_MESSAGE_PARAM
import ru.yundon.rss.utils.Constants.KEY_ARGS
import ru.yundon.rss.utils.Constants.MESSAGE_ERROR
import ru.yundon.rss.utils.Constants.MESSAGE_ERROR_ARGS
import ru.yundon.rss.utils.Constants.MESSAGE_IS_FAVORITES
import ru.yundon.rss.utils.Constants.MESSAGE_IS_NOT_FAVORITES
import ru.yundon.rss.utils.MakeToast.toast
import javax.inject.Inject

class FragmentNews: Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding
        get() = _binding ?: throw RuntimeException(EXCEPTION_MESSAGE_PARAM)

    private var adapterRss: RssAdapter = RssAdapter()

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
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val newsName = requireArguments().getString(KEY_ARGS)

        if (newsName != null) {
            requestApi(newsName)
            getRssEntityList(newsName)
        } else{
            throw RuntimeException(MESSAGE_ERROR_ARGS)
        }

        setupRecyclerView()
        observeRssList()


    }

    private fun requestApi(newsName: String) = with(viewModelRss){
        loadRssInfo(newsName)
    }

    private fun getRssEntityList(newsName: String) = with(viewModelRss){
        getListRssEntity(newsName)
    }

    private fun setupRecyclerView() {
        binding.rssRecycler.apply {
            adapter = adapterRss
        }

        adapterRss.apply {
            itemClickListener = {
                ChromeCustomTabHelper.openCct(requireContext(), it.link)
            }
            itemFavoritesListener = {
                viewModelRss.setFavoritesStatus(it)
                toast (requireContext(),
                    if (it.isFavorites) MESSAGE_IS_NOT_FAVORITES
                    else MESSAGE_IS_FAVORITES
                )
            }
        }
    }

    private fun observeRssList(){

        viewModelRss.apply {

            getListRss.observe(viewLifecycleOwner){
                adapterRss.submitList(it)
            }

            errorConnection.observe(viewLifecycleOwner){
                if (!it) toast(requireContext(), MESSAGE_ERROR)
            }

            isLoading.observe(viewLifecycleOwner){
                binding.progressBarLoading.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }
}

