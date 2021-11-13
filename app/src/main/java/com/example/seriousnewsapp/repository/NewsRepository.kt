package com.example.seriousnewsapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quotesmvvmcoroutines.utils.NetworkUtils
import com.example.seriousnewsapp.api.NewsService
import com.example.seriousnewsapp.model.News
import retrofit2.Response

class NewsRepository(private val newsService: NewsService, private val applicationContext: Context)
{

    companion object
    {
        private const val PREFERENCE_NAME = "NEWS_PREFERENCE"
        private const val HEADLINE_COUNTRY_KEY = "CONTYR"
    }

    //for the get every thing by topic
    private val newsLiveData = MutableLiveData<News>()
    public val news: LiveData<News>
        get() = newsLiveData

    //for the get headlines by country
    private val headlinesLiveData = MutableLiveData<News>()
    public val headlines: LiveData<News>
        get() = headlinesLiveData

    var count: Int = 0//for logging


    suspend fun getCategory(country: String, page: Int, category: String): Response<News>?
    {
        if (NetworkUtils.isInternetAvailable(applicationContext))
        {
            val result = newsService.getByCountryCatagory(country, category, page)
            if (result?.body() != null)
            {
                return result
            }
            else
            {
                return null
            }
        }
        else
        {
            return null
        }
    }

    suspend fun getEveryThingTopic(q: String, page: Int): Response<News>?
    {
        count++
        Log.d("repository", "Topic-${count.toString()} and page= $page")
        if (NetworkUtils.isInternetAvailable(applicationContext))
        {
            val result = newsService.getEveryTopic(q, page)
            if (result?.body() != null)
            {
                //TODO: add the data in the database
                return result

            }
            else
            {
                return null
            }
        }
        else
        {
            //TODO: get the data from the database and display it
            return null
        }
    }


    suspend fun getHeadlinesResponse(country: String, page: Int): Response<News>?
    {
        count++
        Log.d("repository", "Headline- ${count.toString()} and page= $page")
        if (NetworkUtils.isInternetAvailable(applicationContext))
        {
            val result = newsService.getHeadlinesCountry(country, page)
            if (result?.body() != null)
            {
                //TODO: add the data in the database
                return result

            }
            else
            {
                return null
            }
        }
        else
        {
            //TODO: get the data from the database and display it
            return null
        }
    }

    fun setHeadlineCountryShared(country: String)
    {
        val sharedPreferences = applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(HEADLINE_COUNTRY_KEY, country)
        editor.apply()

    }

    fun getHeadlneCountryShared(): String?
    {
        val sharedPreferences = applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(HEADLINE_COUNTRY_KEY, "us")
    }


}