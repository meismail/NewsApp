package com.example.newsapp

//بتطبع البيانات جاهزة data class
data class News(
    val status: String,
    val totalResults: Int,
    val articles: ArrayList<Article>
)

data class Sources(
    val id: String,
    val name: String,
)

data class Article(
    val source: Sources,
    val id: String,
    val name: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
)