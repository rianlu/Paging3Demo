package com.example.paging3demo

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
    }

    fun <T> create(mClass: Class<T>) : T {
        return retrofit.create(mClass)
    }
}