package com.example.newsapp.common

import com.example.newsapp.interfaces.NewsService
import com.example.newsapp.remote.RetrofitClient

import java.lang.StringBuilder

object Common {
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "6d164a0d5a024dd2a181fa35d01e5bbf"

    val newsService:NewsService
        get()= RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source: String):String{
        val apiUrl = StringBuilder("http://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }
}