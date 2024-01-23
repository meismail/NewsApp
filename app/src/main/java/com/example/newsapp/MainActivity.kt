package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ActivityMainBinding
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    //
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Log.d("TAG", "message")
        getNews()

        binding.swipeRefresh.setOnRefreshListener {
            getNews()
        }
        val adRequest = AdManagerAdRequest.Builder().build()
        binding.adManagerAdView.loadAd(adRequest)
    }

    //Retrofit Request function --3rd part of receiving data
    //need 5 info  callable< [GET+EndPoint] + data + Model > + retrofit < BaseUrl + converter >
    private fun getNews() {
        val cat = intent.getStringExtra("cat")!!
        val code = getSharedPreferences("country", MODE_PRIVATE)!!
            .getString("code", "us")
        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create()).build()

        //to communicate with callable
        val callable = retrofit.create(NewsApiCallable::class.java)
        callable.GetFromServerToNews(cat, code).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val articles = response.body()?.articles!!
                //ميزة استخدامData class و ليس Class عادي
                // البيانات المستلمة تطبع مباشرة
                Log.d("trace", "Date: $articles")

                val adaptor = NewsAdaptor(this@MainActivity, articles)
                binding.newsListRV.adapter = adaptor
                //remove view after doing its action
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("trace", "Error: ${t.localizedMessage}")
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }
        })

    }


}