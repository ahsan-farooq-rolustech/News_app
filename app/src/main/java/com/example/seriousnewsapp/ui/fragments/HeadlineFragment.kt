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
import com.example.seriousnewsapp.R
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.databinding.FragmentHeadlineBinding
import com.example.seriousnewsapp.ui.adapter.HeadlinePagingAdapter
import com.example.seriousnewsapp.utils.Constants
import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HeadlineFragment : Fragment()
{
    lateinit var adapter: ArrayAdapter<String>
    lateinit var pagingAdapter: HeadlinePagingAdapter
    lateinit var binding: FragmentHeadlineBinding
    lateinit var mainViewModel: MainViewModel

    val TAG = "lolololololo"
    var flag: Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment

        binding = FragmentHeadlineBinding.inflate(layoutInflater, container, false)

        val repository = (requireActivity().application as NewsApplication).newsRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
        loadCountriesSpinner()
        val country: String = mainViewModel.getHeadlineCountryShared()!!
        val position: Int = adapter.getPosition(country)
        binding.countrySpinner.setSelection(position)
        flag = false

        //binding.headLinesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        pagingAdapter = HeadlinePagingAdapter(requireContext(), binding.newsOfTheDayImg, binding.newsOfTheDayTitle)
        binding.countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
            {
                mainViewModel.setHeadlineCountryShared(Constants.countrySpinnerList[p2])
                loadData()
                flag = true
            }

            override fun onNothingSelected(p0: AdapterView<*>?)
            {
                TODO("Not yet implemented")
            }

        }

        setupLayoutManager()


        val thisFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.headlineFragment)







        return binding.root
    }

    private fun loadCountriesSpinner()
    {
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, Constants.countrySpinnerList)


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
        //for the stack layout manager
//        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
//        layoutManager.setPagerMode(true)
//        layoutManager.setPagerFlingVelocity(3000)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        binding.headLinesRv.layoutManager = layoutManager
        binding.headLinesRv.adapter = pagingAdapter
    }

    override fun onResume()
    {
        super.onResume()


    }

}