package com.example.paging3demo

import com.example.paging3demo.model.ArticleInfo
import com.example.paging3demo.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WanAndroidApi {

    @GET("article/list/{page}/json")
    suspend fun getArticles(
        @Path("page") page: Int
    ) : BaseResponse<ArticleInfo>
}