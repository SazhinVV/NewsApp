package com.example.newsapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.holders.ListNewsViewHolder
import com.example.newsapp.common.ISO8601Parser
import com.example.newsapp.interfaces.ItemClickListener
import com.example.newsapp.models.Article
import com.example.newsapp.NewsDetail
import com.example.newsapp.R
import com.squareup.picasso.Picasso
import java.lang.StringBuilder
import java.text.ParseException
import java.util.*


class ListNewsAdapter (val articleList: MutableList<Article>, private  val context: Context):RecyclerView.Adapter<ListNewsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_layout, parent, false)
        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount () = articleList.size

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        Picasso.get()
            .load(articleList[position].urlToImage)
            .into(holder.article_image)

        articleList[position].title?.let {
            if (it.length > 65) {
                    holder.article_title.text = StringBuilder(it.substring(0, 65)).append("...").toString()
            }else {
                    holder.article_title.text = it
            }
        }

        if (articleList[position].publishedAt != null) {
            var date: Date? = null
            try {
                articleList[position].publishedAt?.let {
                    date =ISO8601Parser.parse(it)
                }
            }catch (ex: ParseException) {
                ex.printStackTrace()
            }
            date?.let {
                holder.article_time.setReferenceTime(it.time)
            }
        }

        holder.setItemClickListener(object : ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val detail = Intent(context, NewsDetail::class.java)
                detail.putExtra("webURL", articleList[position].url)
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(detail)
            }
        })
    }

}