package com.example.newson

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewsItemCLicked {
    private lateinit var mAdepter: NewsListAdepter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView1.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdepter= NewsListAdepter(this)
        recyclerView1.adapter = mAdepter
    }
    private fun fetchData(){
        val url = "https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest = JsonObjectRequest(

            Request.Method.GET,
            url,
            null,
            Response.Listener{
                val newsjsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<news>()
                for(i in 0 until newsjsonArray.length()){
                    val newsjsonObject = newsjsonArray.getJSONObject(i)
                    val News = news(
                                newsjsonObject.getString("title"),
                                newsjsonObject.getString("author"),
                                newsjsonObject.getString("url"),
                                newsjsonObject.getString("urlToImage")
                    )
                    newsArray.add(News)
                }
                    mAdepter.updateNews((newsArray))
            },
            Response.ErrorListener{

            })


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: news) {

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }
}


