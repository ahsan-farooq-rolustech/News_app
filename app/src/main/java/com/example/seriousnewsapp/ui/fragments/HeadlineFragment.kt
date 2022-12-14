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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriousnewsapp.R
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.databinding.FragmentHeadlineBinding
import com.example.seriousnewsapp.ui.adapter.HeadlinePagingAdapter
import com.example.seriousnewsapp.interfaces.AppInterfaces
import com.example.seriousnewsapp.utils.AppConstants
import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HeadlineFragment : Fragment(), AdapterView.OnItemSelectedListener, AppInterfaces
{
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var pagingAdapter: HeadlinePagingAdapter
    private lateinit var binding: FragmentHeadlineBinding
    private lateinit var mainViewModel: MainViewModel

    private val TAG = "lolololololo"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        binding = FragmentHeadlineBinding.inflate(layoutInflater, container, false)
        setMainViewModel()
        loadCountriesSpinner()
        setListinners()
        setSpinners()
        setPagingAdapter()
        setCountrySpinnerListinner()
        return binding.root
    }

    private fun setSpinners()
    {
        binding.countrySpinner.setSelection(getPositionFromCountruSelected())
    }

    private fun setListinners()
    {
//        binding.countrySpinner.onItemSelectedListener = this
    }

    private fun setCountrySpinnerListinner()
    {
        binding.countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
            {
                mainViewModel.setCountryInSharedPrefs(AppConstants.countrySpinnerList[p2])
                loadData()

            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {
                TODO("Not yet implemented")
            }
        }
    }


    private fun setPagingAdapter()
    {
        pagingAdapter = HeadlinePagingAdapter(requireContext(), binding.newsOfTheDayImg, binding.newsOfTheDayTitle)
        pagingAdapter.setListinner(this)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.headLinesRv.layoutManager = layoutManager
        binding.headLinesRv.adapter = pagingAdapter
    }

    private fun getPositionFromCountruSelected(): Int
    {
        val country: String = mainViewModel.getCountryFromSharesPrefs()!!
        return adapter.getPosition(country)
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
        when (view?.id)
        {
            R.id.country_spinner ->
            {
                changeCountry(position)
            }
        }

    }

    private fun changeCountry(position: Int)
    {
        mainViewModel.setCountryInSharedPrefs(AppConstants.countrySpinnerList[position])
        loadData()
    }

    override fun onNothingSelected(parent: AdapterView<*>?)
    {
    }

    override fun onHeadlineClicked(url: String)
    {
        val action = HeadlineFragmentDirections.actionHeadlineFragmentToNewsDetailFragment(url)
        Navigation.findNavController(binding.root).navigate(action)
    }

}