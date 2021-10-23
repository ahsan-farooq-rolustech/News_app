package com.example.seriousnewsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.seriousnewsapp.databinding.DiscoverRvLayoutBinding
import com.example.seriousnewsapp.model.Article

class DiscoverPagingAdapter(private val context: Context) : PagingDataAdapter<Article, DiscoverPagingAdapter.ViewHolder>(diffCallback)
{
    inner class ViewHolder(val binding: DiscoverRvLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    {

    }

    companion object
    {
        val diffCallback = object : DiffUtil.ItemCallback<Article>()
        {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean
            {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean
            {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val data = getItem(position)
        if(data!=null)
        {
            holder.binding.apply {
                Glide.with(context).load(data.urlToImage).transform(CenterCrop(), RoundedCorners(30)).into(this.discoverRvLayoutImage)
                this.discoverRvLayoutText.text=data.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(DiscoverRvLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}