package com.example.moviecatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatch.R
import com.example.moviecatch.models.Result

class MovieAdapter(private val isFirstScreen: Boolean = true) :
    RecyclerView.Adapter<MovieAdapter.MyCustomHolder>() {

    var liveData: List<Result>? = null

    fun setList(liveData: List<Result>) {
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textTitle = view.findViewById<TextView>(R.id.title)
        private  val txtGenre  = view.findViewById<TextView>(R.id.txtGenre)
        private val posterView = view.findViewById<ImageView>(R.id.posterView)

        fun bind(data: Result) {
            textTitle.text = data.title
            txtGenre.text = "deneme deneme deneme"
            Glide.with(posterView).load("https://image.tmdb.org/t/p/w342/${data.poster_path}").into(posterView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_item, parent, false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bind(liveData!![position])
    }

    override fun getItemCount(): Int {
        return if (liveData == null) {
            0
        } else {
            if (!isFirstScreen) {
                liveData!!.size
            }
            8
        }
    }
}