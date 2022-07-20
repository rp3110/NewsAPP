package com.example.newson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdepter(private val listener:NewsItemCLicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val items: ArrayList<news> =ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder

    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentitem = items[position]
        holder.titleView.text= currentitem.title
        holder.author.text=currentitem.author
        Glide.with(holder.itemView.context).load(currentitem.imageURL).into(holder.image)
    }


    fun updateNews(updatedNews:ArrayList<news>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView:TextView=itemView.findViewById(R.id.title)
        val image:ImageView=itemView.findViewById(R.id.imageView)
        val author:TextView=itemView.findViewById((R.id.author))

}
interface NewsItemCLicked{
    fun onItemClicked(item: news)
}