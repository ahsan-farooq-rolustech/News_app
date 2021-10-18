package com.example.seriousnewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.databinding.FragmentHeadlineBinding


import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory

import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.seriousnewsapp.R

import com.example.seriousnewsapp.ui.adapter.HeadlinePagingAdapter
import com.example.seriousnewsapp.utils.Constants
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HeadlineFragment : Fragment()
{
    lateinit var pagingAdapter:HeadlinePagingAdapter
    lateinit var binding: FragmentHeadlineBinding
    lateinit var mainViewModel: MainViewModel

    val TAG = "lolololololo"





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment

        binding = FragmentHeadlineBinding.inflate(layoutInflater, container, false)

        val repository = (requireActivity().application as NewsApplication).newsRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
        loadCountriesSpinner()


        //binding.headLinesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        pagingAdapter= HeadlinePagingAdapter(requireContext(),binding.newsOfTheDayImg,binding.newsOfTheDayTitle)


        setupLayoutManager()


//        mainViewModel.headlines.observe(requireActivity(), {
//            totalResults = it.totalResults
//            Glide.with(requireContext()).load(it.articles[0].urlToImage).transform(CenterCrop(), RoundedCorners(54)).into(binding.newsOfTheDayImg)
//            Log.d(TAG, "${it.totalResults.toString()} and list size ${it.articles.size}")
//            binding.newsOfTheDayTitle.text = it.articles[0].title
//            adapter = HeadlineAdapter(requireContext(), it.articles as MutableList<Article>)
//            binding.headLinesRv.adapter = adapter
//
//
//        })

        loadData()


        val thisFragment=requireActivity().supportFragmentManager.findFragmentById(R.id.headlineFragment)


        binding.countrySpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
            {
                mainViewModel.setHeadlineCountryShared(Constants.countrySpinnerList[p2])
                if (thisFragment != null)
                {
                    requireActivity().supportFragmentManager.beginTransaction().detach(thisFragment).attach(HeadlineFragment()).commit()
                };
            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {
                TODO("Not yet implemented")
            }

        }




        return binding.root
    }

    private fun loadCountriesSpinner()
    {
        val adapter =ArrayAdapter(requireContext(),android. R.layout.simple_spinner_item, Constants.countrySpinnerList)


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countrySpinner.adapter=adapter

        val country:String= mainViewModel.getHeadlineCountryShared()!!
        val position:Int=adapter.getPosition(country)
        binding.countrySpinner.setSelection(position)

    }

    private fun loadData()
    {

        lifecycleScope.launch{
            mainViewModel.headlinesList.collect {
                pagingAdapter.submitData(it)
                Log.d(TAG,it.toString())
            }
        }

    }

    private fun setupLayoutManager()
    {
        //for the stack layout manager
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)


        binding.headLinesRv.layoutManager = layoutManager
        binding.headLinesRv.adapter=pagingAdapter
    }


}