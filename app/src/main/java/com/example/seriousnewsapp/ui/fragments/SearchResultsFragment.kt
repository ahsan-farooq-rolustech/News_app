package com.example.seriousnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.databinding.FragmentSearchResultsBinding
import com.example.seriousnewsapp.interfaces.AppInterfaces
import com.example.seriousnewsapp.ui.adapter.SearchPagingAdapter
import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SearchResultsFragment : Fragment(), AppInterfaces
{

    private lateinit var bindind: FragmentSearchResultsBinding
    private lateinit var pagingAdapter: SearchPagingAdapter
    private lateinit var mainViewModel: MainViewModel
    private val TAG = "SearchFragment"
    private var param1: String? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("searchKeyword")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        bindind = FragmentSearchResultsBinding.inflate(layoutInflater, container, false)
        setMainViewModel()
        setPagingAdapter()
        setLayoutManager()
        loadData()
        return bindind.root
    }

    private fun setMainViewModel()
    {
        val repository = (requireActivity().application as NewsApplication).newsRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository = repository)).get(MainViewModel::class.java)
    }

    private fun setPagingAdapter()
    {
        pagingAdapter = SearchPagingAdapter(requireContext())
        pagingAdapter.setListinner(this)
    }

    private fun loadData()
    {
        val keywords = param1
        lifecycleScope.launch {
            if (keywords != null)
            {
                mainViewModel.getEveryThingTopic(keywords).collect {
                    pagingAdapter.submitData(it)
                    Log.d(TAG, it.toString())
                }
            }
        }
    }

    private fun setLayoutManager()
    {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bindind.searchRecyclerView.layoutManager = layoutManager
        bindind.searchRecyclerView.adapter = pagingAdapter
    }

    override fun onSearchResultCLicked(url: String)
    {
        val action = SearchResultsFragmentDirections.actionSearchResultsFragmentToNewsDetailFragment(url)
        Navigation.findNavController(bindind.root).navigate(action)
    }

}