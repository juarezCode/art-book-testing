package com.juarezcode.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.juarezcode.artbooktesting.R
import com.juarezcode.artbooktesting.roomdb.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {


    class ArtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }


    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row, parent, false)
        return ArtViewHolder(view)
    }


    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imgRow)
        val nameText = holder.itemView.findViewById<TextView>(R.id.txtRowArtName)
        val artistNameText = holder.itemView.findViewById<TextView>(R.id.txtRowArtistName)
        val yearText = holder.itemView.findViewById<TextView>(R.id.txtRowYear)
        val art = arts[position]
        holder.itemView.apply {
            glide.load(art.imageUrl).into(imageView)
            nameText.text = "Name: ${art.name}"
            artistNameText.text = "Artist Name: ${art.artistName}"
            yearText.text = "Year: ${art.year}"
        }
    }

    override fun getItemCount(): Int {
        return arts.size
    }

}