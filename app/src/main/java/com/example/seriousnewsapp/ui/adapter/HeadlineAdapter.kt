package com.example.seriousnewsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seriousnewsapp.databinding.HeadlinesRvLayoutBinding
import com.example.seriousnewsapp.model.Article

class HeadlineAdapter(val context: Context, val list: List<Article>) : RecyclerView.Adapter<HeadlineAdapter.ViewHolder>()
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
    }

    override fun getItemCount(): Int
    {
        return list.size
    }


}