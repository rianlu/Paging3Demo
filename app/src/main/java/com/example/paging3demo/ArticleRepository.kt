package com.example.paging3demo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging3demo.model.Article
import kotlinx.coroutines.flow.Flow

class ArticleRepository {

    fun getArticles(
        articleApi: WanAndroidApi
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 20),
            pagingSourceFactory = { ArticlePagingSource(articleApi) }
        ).flow
    }
}