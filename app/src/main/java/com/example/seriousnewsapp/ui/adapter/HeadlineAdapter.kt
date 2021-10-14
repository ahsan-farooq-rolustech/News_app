package com.example.seriousnewsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seriousnewsapp.application.NewsApplication
import com.example.seriousnewsapp.databinding.HeadlinesRvLayoutBinding
import com.example.seriousnewsapp.model.Article
import com.example.seriousnewsapp.viewModel.MainViewModel
import com.example.seriousnewsapp.viewModel.MainViewModelFactory

class HeadlineAdapter(val context: Context, val list: MutableList<Article>) : RecyclerView.Adapter<HeadlineAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: HeadlinesRvLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(HeadlinesRvLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val data = list[position]
        Glide.with(context).load(data.urlToImage).into(holder.binding.headlineRvImage)
        holder.binding.headlineRvTextView.text = data.title
       // checkNext(position)
    }

//    private fun checkNext(position: Int)
//    {
//        if(list.size>5&&position==list.size-5)
//        {
//
//        }
//    }

    override fun getItemCount(): Int
    {
        return list.size
    }


}