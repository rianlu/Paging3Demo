package com.example.paging3demo.model

import com.example.paging3demo.model.Article
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticleInfo(
    @SerializedName("curPage")
    val currentPage: Int,
    @SerializedName("datas")
    val articleList: List<Article>
) : Serializable