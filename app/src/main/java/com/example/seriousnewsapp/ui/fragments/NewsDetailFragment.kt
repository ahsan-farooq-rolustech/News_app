package com.example.seriousnewsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.seriousnewsapp.R
import com.example.seriousnewsapp.databinding.FragmentNewsDetailBinding
import kotlinx.coroutines.launch


class NewsDetailFragment : Fragment()
{
    lateinit var binding:FragmentNewsDetailBinding
    val url by navArgs<NewsDetailFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding= FragmentNewsDetailBinding.inflate(layoutInflater,container,false)

        lifecycleScope.launch {
            loadWebView()
        }


        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private suspend fun loadWebView()
    {
        binding.detailsWebView.loadUrl(url.newsUrl)
        binding.detailsWebView.settings.javaScriptEnabled=true
    }


}