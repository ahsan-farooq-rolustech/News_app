package com.example.seriousnewsapp.api

import com.example.seriousnewsapp.model.News
import com.example.seriousnewsapp.utils.AppConstants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService
{

    @GET("/v2/everything?apiKey=$API_KEY&sortBy=popularity")
    suspend fun getEveryTopic(@Query("q")topic:String,@Query("page")page:Int):Response<News>

    @GET("/v2/top-headlines?apiKey=$API_KEY")
    suspend fun getByCountry(@Query("country")country:String,@Query("page")page:Int):Response<News>

    @GET("/v2/top-headlines?apiKey=$API_KEY")
    suspend fun getByCountryCatagory(@Query("country")country:String,@Query("category")catagory:String,@Query("page")page:Int):Response<News>

    @GET("/v2/top-headlines?apiKey=$API_KEY")
    suspend fun getHeadlinesCountry(@Query("country")country: String,@Query("page")page:Int):Response<News>


}