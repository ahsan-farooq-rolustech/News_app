package com.example.seriousnewsapp.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.seriousnewsapp.databinding.DiscoverRvLayoutBinding
import com.example.seriousnewsapp.model.Article

class DiscoverPagingAdapter(private val context: Context):PagingDataAdapter<Article,DiscoverPagingAdapter.ViewHolder>(diffCallback)
{
    inner class ViewHolder(val binding: DiscoverRvLayoutBinding):RecyclerView.ViewHolder(binding.root)
    {

    }
    companion object
    {
        val diffCallback=object :DiffUtil.ItemCallback<Article>()
        {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean
            {
                return oldItem.url==newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean
            {
                return oldItem==newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        TODO("Not yet implemented")
    }
}