package com.example.newsapp.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Common.ISO8601Parser
import com.example.newsapp.Interface.ItemClickListener
import com.example.newsapp.Model.Article
import com.example.newsapp.NewsDetail
import com.example.newsapp.R
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.util.*


class ListNewsAdapter (val articleList: MutableList<Article>, private  val context: Context):RecyclerView.Adapter<ListNewsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.news_layout, parent, false)
        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {

        Picasso.get()
            .load(articleList[position].urlToImage)
            .into(holder.article_image)
        if (articleList[position].title!!.length > 65) {
            holder.article_title.text = articleList[position].title!!.substring(0, 65)+"..."
        }else {
            holder.article_title.text = articleList[position].title!!
        }

        if (articleList[position].publishedAt != null) {
            var date: Date? = null
            try {
                date =ISO8601Parser.parse(articleList[position].publishedAt!!)
            }catch (ex: ParseException) {
                ex.printStackTrace()
            }
            holder.article_time.setReferenceTime(date!!.time)
        }

        holder.setItemClickListener(object : ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val detail = Intent(context, NewsDetail::class.java)
                detail.putExtra("webURL", articleList[position].url)
                Log.d("MyTAG",detail.getStringExtra("webURL"))
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(detail)
            }
        })


    }

}