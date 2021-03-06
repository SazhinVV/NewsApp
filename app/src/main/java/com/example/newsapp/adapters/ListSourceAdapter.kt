package com.example.newsapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.holders.ListSourceViewHolder
import com.example.newsapp.interfaces.ItemClickListener
import com.example.newsapp.ListNews
import com.example.newsapp.models.WebSite
import com.example.newsapp.R

class ListSourceAdapter (private val context: Context, private val webSite: WebSite):RecyclerView.Adapter<ListSourceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.source_news_layout, parent, false)
        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount()= webSite.sources!!.size

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        webSite.sources?.let {
            holder.source_title.text = it[position].name
        }
        val x = (1..5).shuffled().first()
        when(x){
            1 -> holder.source_image.setImageResource(R.drawable.news_source_logo_1)
            2 -> holder.source_image.setImageResource(R.drawable.news_source_logo_2)
            3 -> holder.source_image.setImageResource(R.drawable.news_source_logo_3)
            4 -> holder.source_image.setImageResource(R.drawable.news_source_logo_4)
            5 -> holder.source_image.setImageResource(R.drawable.news_source_logo_5)
        }

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, ListNews::class.java)
                intent.putExtra("source", webSite.sources!![position].id)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent)
            }
        }){
        }
    }
}