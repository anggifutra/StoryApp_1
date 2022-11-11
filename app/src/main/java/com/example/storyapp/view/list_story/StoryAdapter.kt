package com.example.storyapp.view.list_story

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.data.ListStory
import com.squareup.picasso.Picasso

class StoryAdapter(private val listStory: ArrayList<ListStory>) : RecyclerView.Adapter<StoryAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: ListStory)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_story,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val(id,name,token,photoUrl) = listStory[position]
        Picasso.get()
            .load(photoUrl)
            .into(viewHolder.iv_item_photo)
        viewHolder.tv_item_name.text = name
        viewHolder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(listStory[viewHolder.adapterPosition])}
    }

    override fun getItemCount() = listStory.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_item_photo: ImageView = view.findViewById(R.id.iv_item_photo)
        val tv_item_name: TextView = view.findViewById(R.id.tv_item_name)
    }

}