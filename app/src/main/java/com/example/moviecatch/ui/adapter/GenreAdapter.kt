package com.example.moviecatch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatch.R

import com.example.moviecatch.models.GenreX


class GenreAdapter : RecyclerView.Adapter<GenreAdapter.MyCustomHolder>() {

    var genreList: List<GenreX>? = null;

    fun setList(genreList: List<GenreX>) {

        this.genreList = genreList
        notifyDataSetChanged()
    }

    class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        val genreName = view.findViewById<TextView>(R.id.genreName)
        fun bind(data: GenreX) {
            genreName.text = data.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCustomHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        return MyCustomHolder(view);
    }

    override fun getItemCount(): Int {
        return if (genreList == null) {
            0
        } else {
            return genreList!!.size
        }
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bind(genreList!![position])
    }
}