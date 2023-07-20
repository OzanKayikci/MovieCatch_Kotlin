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

class RecentMovieAdapter(private val isFirstScreen: Boolean = true) :
    RecyclerView.Adapter<RecentMovieAdapter.MyCustomHolder>() {

    var liveData: List<Result>? = null;

    fun setList(liveData: List<Result>) {
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textTitle: TextView = view.findViewById<TextView>(R.id.title)
        private  val txtGenre :TextView = view.findViewById<TextView>(R.id.txtGenre)
        private val posterView = view.findViewById<ImageView>(R.id.posterView)
        private val txtReleaseDate = view.findViewById<TextView>(R.id.txtReleaseDate)
        private  val txtVoteAverage = view.findViewById<TextView>(R.id.txtVoteAverage)
        fun bind(data: Result) {
            textTitle.text = data.title
            txtGenre.text = "deneme deneme deneme"
            txtReleaseDate.text = data.release_date
            txtVoteAverage.text = data.vote_average.toString()+"/ 10"
            Glide.with(posterView).load("https://image.tmdb.org/t/p/w342/${data.poster_path}").into(posterView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMovieAdapter.MyCustomHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recent_movie_item, parent, false)
        return MyCustomHolder(view);
    }

    override fun onBindViewHolder(holder: RecentMovieAdapter.MyCustomHolder, position: Int) {
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