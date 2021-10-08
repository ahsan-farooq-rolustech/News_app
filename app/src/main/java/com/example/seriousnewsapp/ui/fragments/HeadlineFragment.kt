package com.example.seriousnewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.databinding.FragmentHeadlineBinding

import com.example.seriousnewsapp.ui.adapter.HeadlineAdapter
import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

import androidx.core.graphics.drawable.RoundedBitmapDrawable

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.RoundedCorner
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.littlemango.stacklayoutmanager.StackLayoutManager


class HeadlineFragment : Fragment()
{
    lateinit var binding: FragmentHeadlineBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: HeadlineAdapter
    val TAG="lolololololo"
    var totalResults:Int=0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment

        binding = FragmentHeadlineBinding.inflate(layoutInflater, container, false)

        val repository = (requireActivity().application as NewsApplication).newsRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
     //   binding.headLinesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        //for the stack layout manager
        val layoutManager= StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        layoutManager.setItemChangedListener(object: StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int)
            {

                Log.d(TAG,"First visibe item-${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG,"Total count-${layoutManager.itemCount}")
                if(totalResults>layoutManager.itemCount&&layoutManager.getFirstVisibleItemPosition()>=layoutManager.itemCount-5)
                {
                    //TODO: get the material for hte next page number
                }
            }
        })

        binding.headLinesRv.layoutManager=layoutManager


        mainViewModel.headlines.observe(requireActivity(), {
            totalResults=it.totalResults
            Glide.with(requireContext()).load(it.articles[0].urlToImage).transform(CenterCrop(),RoundedCorners(54)).into(binding.newsOfTheDayImg)
            Log.d(TAG,"${it.totalResults.toString()} and list size ${it.articles.size}")
            binding.newsOfTheDayTitle.text = it.articles[0].title
            adapter = HeadlineAdapter(requireContext(), it.articles)
            binding.headLinesRv.adapter = adapter
        })





        return binding.root
    }

}