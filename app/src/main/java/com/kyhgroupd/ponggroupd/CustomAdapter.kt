package com.kyhgroupd.ponggroupd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

internal class CustomAdapter(private var listItems: MutableList<PlayerScore>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items, parent, false)
        return CustomViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val listItem = listItems[position]
        holder.nameTextView.text = listItem.username
        holder.scoreTextView.text = listItem.score.toString()
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    internal inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView: TextView = view.findViewById(R.id.tvName)
        var scoreTextView: TextView = view.findViewById(R.id.tvScore)
    }

}