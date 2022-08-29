package com.example.seriousnewsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.seriousnewsapp.databinding.FragmentNewsDetailBinding
import kotlinx.coroutines.launch


class NewsDetailFragment : Fragment()
{
    private lateinit var binding: FragmentNewsDetailBinding
    private val url by navArgs<NewsDetailFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentNewsDetailBinding.inflate(layoutInflater, container, false)

        launchWebView()


        return binding.root
    }

    private fun launchWebView()
    {
        lifecycleScope.launch {
            loadWebView()
        }
    }

    @SuppressLint("SetJavaScriptEnabled") private suspend fun loadWebView()
    {
        binding.detailsWebView.loadUrl(url.newsUrl)
        binding.detailsWebView.settings.javaScriptEnabled = true
    }


}