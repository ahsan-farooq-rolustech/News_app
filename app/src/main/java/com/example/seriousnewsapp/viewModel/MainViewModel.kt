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
import com.example.seriousnewsapp.paging.NewsPagingSource
import com.example.seriousnewsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NewsRepository) : ViewModel()
{


    public val headlines: LiveData<News>
        get() = repository.headlines

    public var headlineCountry: String? =null

    init
    {
        headlineCountry= repository.getHeadlneCountryShared()
    }





    public fun getHeadlinesCountry(): Flow<PagingData<Article>>
    {

        refreshHeadlineCountryShared()
        val headlinesList = Pager(PagingConfig(pageSize = 1))
        {
            NewsPagingSource(repository, headlineCountry!!)
        }.flow.cachedIn(viewModelScope)

        return headlinesList

    }


    public fun setHeadlineCountryShared(country: String)
    {
        repository.setHeadlineCountryShared(country)
    }

    public fun getHeadlineCountryShared(): String?
    {

        headlineCountry=repository.getHeadlneCountryShared()
        return headlineCountry
    }

    public fun refreshHeadlineCountryShared()
    {
        headlineCountry=repository.getHeadlneCountryShared()
    }

}