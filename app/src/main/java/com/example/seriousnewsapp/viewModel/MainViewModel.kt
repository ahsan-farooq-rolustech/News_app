package com.example.seriousnewsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriousnewsapp.model.News
import com.example.seriousnewsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.SoftReference

class MainViewModel(private val repository: NewsRepository) : ViewModel()
{
    init
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.getHeadlinesCountry("us")
        }
    }
    public val headlines: LiveData<News>
        get() = repository.headlines

    public suspend fun getHeadlinesCountry(country: String): LiveData<News>
    {
        val job = viewModelScope.launch(Dispatchers.IO)
        {
            repository.getHeadlinesCountry(country)
            repository.headlines
        }


        return repository.headlines
    }
}