package com.example.paging3demo

import androidx.paging.PagingSource
import com.example.paging3demo.model.Article
import java.lang.Exception

class ArticlePagingSource(
    private val articleApi: WanAndroidApi
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 0
        return try {
            val response = articleApi.getArticles(page)
            if (response.errorCode == 0) {
                LoadResult.Page(
                    data = response.data.articleList,
                    prevKey = null,
                    nextKey = if (response.data.articleList.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable(response.errorMsg))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}