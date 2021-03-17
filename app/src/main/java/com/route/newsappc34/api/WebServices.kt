package com.route.newsappc34.api

import com.route.newsappc34.api.model.NewsResponse
import com.route.newsappc34.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("sources")
    fun getNewsSources(@Query("apiKey") key:String,
                       @Query("language")lang:String,
                       @Query("country")country:String):Call<SourcesResponse>
    @GET("everything")
    fun getNews(@Query("apiKey") key:String,
                       @Query("language")lang:String,
                       @Query("sources")sources:String,
                       @Query("q")searchKeyword:String):Call<NewsResponse>

}