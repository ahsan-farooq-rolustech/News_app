package com.example.seriousnewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriousnewsapp.R
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.databinding.FragmentDiscoverBinding
import com.example.seriousnewsapp.ui.adapter.DiscoverPagingAdapter
import com.example.seriousnewsapp.utils.AppConstants
import com.example.seriousnewsapp.utils.CategoryConstants
import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DiscoverFragment : Fragment(), View.OnClickListener
{
    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var padingAdapter: DiscoverPagingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentDiscoverBinding.inflate(layoutInflater, container, false)

        setViewModel()
        setPagingAdapter()
        setAdapter()
        loadData(CategoryConstants.CATEGORY_BUSINESS)
        setDiscoverTabLayout()
        setListinners()

        return binding.root
    }

    private fun setPagingAdapter()
    {
        padingAdapter = DiscoverPagingAdapter(requireContext())
    }

    private fun setViewModel()
    {
        val repository = (requireActivity().application as NewsApplication).newsRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
    }

    private fun setListinners()
    {
        binding.searchImg.setOnClickListener(this)
    }

    private fun setDiscoverTabLayout()
    {
        binding.discoverTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                when (tab!!.text)
                {
                    getString(R.string.business) -> loadData(CategoryConstants.CATEGORY_BUSINESS)
                    getString(R.string.entertainment) -> loadData(CategoryConstants.CATEGORY_ENTERTAINMENT)
                    getString(R.string.general) -> loadData(CategoryConstants.CATEGORY_GENERAL)
                    getString(R.string.health) -> loadData(CategoryConstants.CATEGORY_HEALTH)
                    getString(R.string.science) -> loadData(CategoryConstants.CATEGORY_SCIENCE)
                    getString(R.string.sports) -> loadData(CategoryConstants.CATEGORY_SPORTS)
                    getString(R.string.technology) -> loadData(CategoryConstants.CATEGORY_TECHNOLOGY)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?)
            {

            }

            override fun onTabReselected(tab: TabLayout.Tab?)
            {

            }

        })
    }

    private fun setAdapter()
    {
        binding.discoverRv.apply {
            this.adapter = padingAdapter
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun loadData(category: String)
    {
        lifecycleScope.launch {
            mainViewModel.getCategory(category).collect {
                padingAdapter.submitData(it)
            }
        }
    }

    override fun onClick(v: View?)
    {
        when (v?.id)
        {
            binding.searchImg.id ->
            {
                onSearchImgClicked()
            }
        }
    }

    private fun onSearchImgClicked()
    {
        binding.etSearch.apply {
            if (this.text.isNullOrEmpty())
            {
                this.error = AppConstants.ERROR_ENTER_KEYWORD
            }
            else
            {
                navigateToDiscoverFragment(this)

            }
        }
    }

    private fun navigateToDiscoverFragment(etSearch: EditText)
    {
        val action = DiscoverFragmentDirections.actionDiscoverFragmentToSearchResultsFragment(etSearch.text.toString())
        Navigation.findNavController(etSearch).navigate(action)
    }


}