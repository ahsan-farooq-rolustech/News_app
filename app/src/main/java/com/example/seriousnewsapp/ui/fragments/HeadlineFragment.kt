package com.example.seriousnewsapp.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.databinding.FragmentHeadlineBinding
import com.example.seriousnewsapp.ui.adapter.HeadlinePagingAdapter
import com.example.seriousnewsapp.utils.AppConstants
import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HeadlineFragment : Fragment(), AdapterView.OnItemSelectedListener
{
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var pagingAdapter: HeadlinePagingAdapter
    private lateinit var binding: FragmentHeadlineBinding
    private lateinit var mainViewModel: MainViewModel

    val TAG = "lolololololo"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        binding = FragmentHeadlineBinding.inflate(layoutInflater, container, false)
        setMainViewModel()
        loadCountriesSpinner()
        setListinners()
        binding.countrySpinner.setSelection(getPositionFromCountruSelected())
        setPagingAdapter()
        // setSpinners()
        setupLayoutManager()
        return binding.root
    }

    private fun setListinners()
    {
        binding.countrySpinner.onItemSelectedListener = this
    }

//    private fun setSpinners()
//    {
//        binding.countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
//        {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
//            {
//
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?)
//            {
//                TODO("Not yet implemented")
//            }
//        }
//    }


    private fun setPagingAdapter()
    {
        pagingAdapter = HeadlinePagingAdapter(requireContext(), binding.newsOfTheDayImg, binding.newsOfTheDayTitle)
    }

    private fun getPositionFromCountruSelected(): Int
    {
        val country: String = mainViewModel.getCountryFromSharesPrefs()!!
        val position: Int = adapter.getPosition(country)
        return position
    }

    private fun setMainViewModel()
    {
        val repository = (requireActivity().application as NewsApplication).newsRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
    }

    private fun loadCountriesSpinner()
    {
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, AppConstants.countrySpinnerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countrySpinner.adapter = adapter
    }

    private fun loadData()
    {
        lifecycleScope.launch {
            mainViewModel.getHeadlinesCountry().collect {
                pagingAdapter.submitData(it)
                Log.d(TAG, it.toString())
            }
        }

    }

    private fun setupLayoutManager()
    {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.headLinesRv.layoutManager = layoutManager
        binding.headLinesRv.adapter = pagingAdapter
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
        when (view?.id)
        {
            binding.countrySpinner.id ->
            {
                changeCountry(position)
            }
        }

    }

    private fun changeCountry(position: Int)
    {
        mainViewModel.setHeadlineCountryShared(AppConstants.countrySpinnerList[position])
        loadData()
    }

    override fun onNothingSelected(parent: AdapterView<*>?)
    {
    }

}