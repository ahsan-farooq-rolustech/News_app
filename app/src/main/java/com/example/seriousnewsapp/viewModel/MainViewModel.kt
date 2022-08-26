package com.example.seriousnewsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.seriousnewsapp.model.Article
import com.example.seriousnewsapp.model.News
import com.example.seriousnewsapp.paging.DiscoveryPagingSource
import com.example.seriousnewsapp.paging.NewsPagingSource
import com.example.seriousnewsapp.paging.SearchPagingSource
import com.example.seriousnewsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NewsRepository) : ViewModel()
{


    public val headlines: LiveData<News>
        get() = repository.headlines

    public var country: String? =null

    init
    {
        country= repository.getHeadlneCountryShared()
    }



    public fun getCategory(category: String):Flow<PagingData<Article>>
    {
        refreshHeadlineCountryShared()
        val list=Pager(PagingConfig(pageSize = 1))
        {
            DiscoveryPagingSource(repository,country!!,category)
        }.flow.cachedIn(viewModelScope)
        return list
    }


    public fun getHeadlinesCountry(): Flow<PagingData<Article>>
    {

        refreshHeadlineCountryShared()
        val headlinesList = Pager(PagingConfig(pageSize = 1))
        {
            NewsPagingSource(repository, country!!)
        }.flow.cachedIn(viewModelScope)

        return headlinesList

    }

    public fun getEveryThingTopic(q: String):Flow<PagingData<Article>>
    {
        refreshHeadlineCountryShared()
        val list=Pager(PagingConfig(pageSize = 1))
        {
            SearchPagingSource(repository,q)
        }.flow.cachedIn(viewModelScope)

        return list
    }


    public fun setHeadlineCountryShared(country: String)
    {
        repository.setHeadlineCountryShared(country)
    }

    public fun getCountryFromSharesPrefs(): String?
    {

        country=repository.getHeadlneCountryShared()
        return country
    }

    public fun refreshHeadlineCountryShared()
    {
        country=repository.getHeadlneCountryShared()
    }

}