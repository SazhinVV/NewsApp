package com.example.newsapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapters.ListNewsAdapter
import com.example.newsapp.common.Common
import com.example.newsapp.interfaces.NewsService
import com.example.newsapp.models.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var source=""
    var webHotUrl: String? = ""

    lateinit var dialog: AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService
        dialog = SpotsDialog(this)
        swipe_to_refresh.setOnRefreshListener { loadNews(source, true) }

        diagonalLayout.setOnClickListener{
            val detailIntent = Intent(baseContext, NewsDetail::class.java)
            detailIntent.putExtra("webURL", webHotUrl)
            startActivity(detailIntent)
        }

        list_news.setHasFixedSize(true)

        list_news.layoutManager = LinearLayoutManager(this)

        if (intent != null) {
            source = intent.getStringExtra("source")
            if (source.isNotEmpty())
                loadNews(source, false)
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {
            if (isRefreshed) {
                dialog.show()
                mService.getNewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : Callback<News>{
                        override fun onFailure(call: Call<News>, t: Throwable) {
                                //
                        }

                        override fun onResponse(call: Call<News>, response: Response<News>) {
                            dialog.dismiss()
                            response.body()?.let {
                            Picasso.get()
                                .load(it.articles!![0].urlToImage)
                                .into(top_image)

                                top_title.text = it.articles!![0].title
                                top_author.text = it.articles!![0].author
                                webHotUrl = it.articles!![0].url
                            }

                            val removeFirstItem = response.body()!!.articles
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem, baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter
                        }
                    })
            }
            else {
                swipe_to_refresh.isRefreshing = true
                mService.getNewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : Callback<News>{
                        override fun onFailure(call: Call<News>, t: Throwable) {
                                //
                        }

                        override fun onResponse(call: Call<News>, response: Response<News>) {
                            swipe_to_refresh.isRefreshing = false
                            response.body()?.let {
                                Picasso.get()
                                    .load(it.articles!![0].urlToImage)
                                    .into(top_image)

                                top_title.text = it.articles!![0].title
                                top_author.text = it.articles!![0].author
                                webHotUrl = it.articles!![0].url
                            }

                            val removeFirstItem = response.body()!!.articles
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem, baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter
                        }
                    })
            }
    }
}
