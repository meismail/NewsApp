package com.example.newsapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.NewsListUnitBinding


class NewsAdaptor(
    private val activity: Activity, val articlesArray: ArrayList<Article>
) : RecyclerView.Adapter<NewsAdaptor.MyViewHolder>() {

    class MyViewHolder(val binding: NewsListUnitBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val binding = NewsListUnitBinding.inflate(
            activity.layoutInflater, parent, false)
        return MyViewHolder(binding)

    }

    override fun getItemCount() = articlesArray.size

    override fun onBindViewHolder(
        holder: MyViewHolder, position: Int) {
        //Log Debug to trace data view
        Log.d("trace", "Link: ${articlesArray[position].urlToImage}")
        holder.binding.newsTitle.text = articlesArray[position].title
        Glide.with(activity).load(articlesArray[position].urlToImage).into(holder.binding.image)
        holder.binding.Card.setOnClickListener {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(articlesArray[position].url)
                )
            )
        }
    }

}