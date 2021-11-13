package com.example.seriousnewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriousnewsapp.R
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.databinding.FragmentDiscoverBinding
import com.example.seriousnewsapp.ui.adapter.DiscoverPagingAdapter
import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DiscoverFragment : Fragment()
{
    lateinit var binding:FragmentDiscoverBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var padingAdapter:DiscoverPagingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding= FragmentDiscoverBinding.inflate(layoutInflater,container,false)

        val repository=(requireActivity().application as NewsApplication).newsRepository
        mainViewModel=ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        padingAdapter= DiscoverPagingAdapter(requireContext())

        binding.discoverRv.apply {
            this.adapter=padingAdapter
            this.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }

        loadData("business")
        binding.discoverTabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                when(tab!!.text)
                {
                    getString(R.string.business) -> loadData("business")
                    getString(R.string.entertainment) -> loadData("entertainment")
                    getString(R.string.general)-> loadData("general")
                    getString(R.string.health)->loadData("health")
                    getString(R.string.science)-> loadData("science")
                    getString(R.string.sports)-> loadData("sports")
                    getString(R.string.technology)-> loadData("technology")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?)
            {

            }

            override fun onTabReselected(tab: TabLayout.Tab?)
            {

            }

        })

        binding.searchImg.setOnClickListener{
            binding.discoverSarch.apply {
                if(this.text.isNullOrEmpty())
                {
                    this.error = "Enter Keyword"
                }
                else
                {
                    val action=DiscoverFragmentDirections.actionDiscoverFragmentToSearchResultsFragment(this.text.toString())
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }

        return binding.root
    }

    private fun loadData(category:String)
    {
        lifecycleScope.launch {
            mainViewModel.getCategory(category).collect {
                padingAdapter.submitData(it)
            }
        }
    }


}