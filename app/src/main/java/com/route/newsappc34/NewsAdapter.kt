package com.route.newsappc34;

import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide
import com.route.newsappc34.api.model.ArticlesItem

public class NewsAdapter(var newsList:List<ArticlesItem?>?):
    RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_news,parent,false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = newsList?.get(position)
        holder.date.setText(newsItem?.publishedAt)
        holder.title.setText(newsItem?.title)
        holder.desc.setText(newsItem?.description)
        Glide.with(holder.itemView)
            .load(newsItem?.urlToImage)
            .into(holder.image)
    }

    fun changeData(newsList: List<ArticlesItem?>?){
        this.newsList=newsList;
        notifyDataSetChanged()
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val date:TextView= itemView.findViewById(R.id.date)
        val title:TextView= itemView.findViewById(R.id.title)
        val image:ImageView= itemView.findViewById(R.id.image)
        val desc:TextView= itemView.findViewById(R.id.desc)

    }
}
