package com.example.seriousnewsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.seriousnewsapp.databinding.HeadlinesRvLayoutBinding
import com.example.seriousnewsapp.model.Article
import com.example.seriousnewsapp.ui.fragments.HeadlineFragment
import com.example.seriousnewsapp.interfaces.AppInterfaces

class HeadlinePagingAdapter(private val context: Context,private val newsOfTheDayImg: ImageView, private val newsOfTheDayTitle: TextView):PagingDataAdapter<Article,HeadlinePagingAdapter.HeadlineViewHolder>(diffCallback)
{
    inner class HeadlineViewHolder(val binding: HeadlinesRvLayoutBinding): RecyclerView.ViewHolder(binding.root)
    {

    }

    private lateinit var mListinner:AppInterfaces

    public fun setListinner(listinner: AppInterfaces)
    {
        this.mListinner=listinner
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

        holder.binding.apply {
            if (data != null)
            {
                this.headlineRvSource.text=data.source.name
                this.headlineRvPublishedAt.text=data.author
            }
        }

        holder.binding.root.setOnClickListener {
//            val action=HeadlineFragmentDirections.actionHeadlineFragmentToNewsDetailFragment(data!!.url)
//            Navigation.findNavController(it).navigate(action)
            if(data!=null)
            {
                mListinner.onHeadlineClicked(data.url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder
    {
       return HeadlineViewHolder(HeadlinesRvLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}