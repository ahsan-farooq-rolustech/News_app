package com.example.seriousnewsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.seriousnewsapp.model.News
import com.example.seriousnewsapp.paging.NewsPagingSource
import com.example.seriousnewsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NewsRepository) : ViewModel()
{



    public val headlines: LiveData<News>
        get() = repository.headlines

    val count:Int=0


    public val headlinesList=Pager(PagingConfig(pageSize = 1))
    {
        NewsPagingSource(repository)
    }.flow.cachedIn(viewModelScope)




    public  fun getHeadlinesCountry(country: String,page:Int)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.getHeadlinesCountry(country,page)

        }



    }

//    public  fun getHeadlinesCountryNextPage(country: String)
//    {
//        this.headlinePage= headlinePage!! +1;
//        viewModelScope.launch(Dispatchers.IO)
//        {
//            repository.getHeadlinesCountry(country,headlinePage!!)
//
//        }
//
//
//    }
}