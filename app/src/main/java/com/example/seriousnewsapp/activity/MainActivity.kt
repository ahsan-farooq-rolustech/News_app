package com.example.seriousnewsapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.seriousnewsapp.R
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity()
{
    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository=(application as NewsApplication).newsRepository
        mainViewModel=ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.headlines.observe(this,{
            Log.d("KHABAR",it.toString())
        })
    }
}