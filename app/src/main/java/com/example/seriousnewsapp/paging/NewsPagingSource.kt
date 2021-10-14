package com.example.seriousnewsapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.seriousnewsapp.model.Article
import com.example.seriousnewsapp.repository.NewsRepository
import java.lang.Exception

class NewsPagingSource(private val repository: NewsRepository):PagingSource<Int,Article>()
{
    companion object
    {
        private var totalNews:Int=0
        private var receivedNews:Int=0
    }
    override fun getRefreshKey(state: PagingState<Int, Article>): Int?
    {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article>
    {
        return try
        {
            val currentPage=params.key?:1
            val response=repository.getHeadlinesResponse("us",currentPage)
            val responseData= mutableListOf<Article>()
            val news=response?.body()
            totalNews= news!!.totalResults
            receivedNews+= news.articles.size
            val data= response.body()?.articles?: emptyList()
            responseData.addAll(data)
            LoadResult.Page(
                data=responseData,
                prevKey =if(currentPage==1)null else currentPage -1,
                nextKey =if(receivedNews< totalNews-1) currentPage.plus(1) else null
            )

        }
        catch (e:Exception)
        {
            LoadResult.Error(e)
        }
    }
}