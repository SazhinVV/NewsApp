package com.example.newsapp.Interface

import com.example.newsapp.Model.News
import retrofit2.Call
import com.example.newsapp.Model.WebSite
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    @get: GET("v2/sources?apiKey=6d164a0d5a024dd2a181fa35d01e5bbf")
    val sources: Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url:String):Call<News>
}