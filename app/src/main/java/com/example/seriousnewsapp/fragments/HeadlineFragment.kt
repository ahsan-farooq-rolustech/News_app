package com.example.seriousnewsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seriousnewsapp.R
import com.example.seriousnewsapp.databinding.FragmentHeadlineBinding


class HeadlineFragment : Fragment()
{
    lateinit var binding:FragmentHeadlineBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment

        binding= FragmentHeadlineBinding.inflate(layoutInflater,container,false)



        return binding.root
    }

}