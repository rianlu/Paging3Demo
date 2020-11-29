package com.example.paging3demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.paging3demo.model.Article
import com.example.paging3demo.ArticleRepository
import com.example.paging3demo.RetrofitUtils
import com.example.paging3demo.WanAndroidApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticleViewModel : ViewModel() {

    private val repository: ArticleRepository by lazy { ArticleRepository() }
    private val articleApi: WanAndroidApi =
        RetrofitUtils.create(WanAndroidApi::class.java)

    fun getArticles() : Flow<PagingData<UiModel>> {
        return repository.getArticles(articleApi)
            .map { pagingData -> pagingData.map {
                UiModel.ArticleItem(
                    it
                )
            } }
            .map {
                it.insertSeparators<UiModel.ArticleItem, UiModel> { before, after ->
                    if (before == null) {
                        return@insertSeparators null
                    }
                    if (after == null) {
                        return@insertSeparators null
                    }
                    return@insertSeparators UiModel.SeparatorItem(
                        after.article.id
                    )
                }
            }
    }

    sealed class UiModel {
        data class ArticleItem(val article: Article) : UiModel()
        // 注意这里不一定要填id，只是需要一个唯一标识
        data class SeparatorItem(val articleId: Long) : UiModel()
    }
}