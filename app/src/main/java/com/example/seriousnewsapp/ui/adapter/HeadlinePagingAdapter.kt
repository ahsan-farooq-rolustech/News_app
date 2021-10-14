package com.example.seriousnewsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.seriousnewsapp.databinding.HeadlinesRvLayoutBinding
import com.example.seriousnewsapp.model.Article

class HeadlinePagingAdapter(private val context: Context,private val newsOfTheDayImg: ImageView, private val newsOfTheDayTitle: TextView):PagingDataAdapter<Article,HeadlinePagingAdapter.HeadlineViewHolder>(diffCallback)
{
    inner class HeadlineViewHolder(val binding: HeadlinesRvLayoutBinding): RecyclerView.ViewHolder(binding.root)
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

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int)
    {
        val data = getItem(position)
        if(position==0)
        {
            if (data != null)
            {
                Glide.with(context).load(data.urlToImage).transform(CenterCrop(), RoundedCorners(54)).into(newsOfTheDayImg)
                newsOfTheDayTitle.text=data.title
            }
        }
        if (data != null)
        {
            Glide.with(context).load(data.urlToImage).into(holder.binding.headlineRvImage)
        }
        if (data != null)
        {
            holder.binding.headlineRvTextView.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder
    {
       return HeadlineViewHolder(HeadlinesRvLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}