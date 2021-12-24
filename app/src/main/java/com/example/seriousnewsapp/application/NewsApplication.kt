package com.example.seriousnewsapp.application

import android.app.Application
import com.example.seriousnewsapp.api.NewsService
import com.example.seriousnewsapp.api.RetrofitHelper
import com.example.seriousnewsapp.repository.NewsRepository

class NewsApplication : Application()
{
    lateinit var newsRepository: NewsRepository
    override fun onCreate()
    {
        super.onCreate()
        initialize()
    }

    private fun initialize()
    {
        val newsService = RetrofitHelper.getInstant().create(NewsService::class.java)
        newsRepository= NewsRepository(newsService,applicationContext)
    }
}