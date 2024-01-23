package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiCallable {
    //@GET(EndPoint)
    @GET("/v2/top-headlines?apiKey=d0e782839b0942c08014f1ca4a18d076")
    //function to get data recieved from server to my JSON Model class "News"
    fun GetFromServerToNews(@Query("category") cat:String?,@Query("country") code: String?):Call<News>
}