package com.example.seriousnewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seriousnewsapp.R
import com.example.seriousnewsapp.databinding.FragmentDiscoverBinding
import com.google.android.material.tabs.TabLayout


class DiscoverFragment : Fragment()
{
    lateinit var binding:FragmentDiscoverBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding= FragmentDiscoverBinding.inflate(layoutInflater,container,false)

        binding.discoverTabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                TODO("Not yet implemented")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?)
            {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?)
            {
                TODO("Not yet implemented")
            }

        })

        return binding.root
    }


}