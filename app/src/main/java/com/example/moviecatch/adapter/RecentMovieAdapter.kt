package com.example.moviecatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatch.R
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.models.Result

class RecentMovieAdapter(private val isFirstScreen: Boolean = true) :
    RecyclerView.Adapter<RecentMovieAdapter.MyCustomHolder>() {

    var liveData: List<Result>? = null;
    var genreList: List<GenreData>? = null;

    fun setList(liveData: List<Result>, genreList: List<GenreData>) {
        this.liveData = liveData
        this.genreList = genreList
        notifyDataSetChanged()
    }

    private fun getGenresNameOfMovie(genreIds: List<Int>, genreList: List<GenreData>): String {
        var genresName: String = ""
        for (id in genreIds) {
            var currentGenre = genreList.find { g -> g.genre_id == id }
            if (currentGenre != null) {
                genresName += "${currentGenre!!.en_name},  "

            }
        }
        genresName = genresName.substring(0, genresName.length-3)
        return genresName
    }

    class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textTitle: TextView = view.findViewById<TextView>(R.id.title)
        private val txtGenre: TextView = view.findViewById<TextView>(R.id.txtGenre)
        private val posterView = view.findViewById<ImageView>(R.id.posterView)
        private val txtReleaseDate = view.findViewById<TextView>(R.id.txtReleaseDate)
        private val txtVoteAverage = view.findViewById<TextView>(R.id.txtVoteAverage)

        fun bind(data: Result, genresName: String) {
            textTitle.text = data.title
            txtGenre.text = genresName
            txtReleaseDate.text = data.release_date
            txtVoteAverage.text = data.vote_average.toString() + "/ 10"
            Glide.with(posterView).load("https://image.tmdb.org/t/p/w342/${data.poster_path}")
                .into(posterView)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentMovieAdapter.MyCustomHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recent_movie_item, parent, false)
        return MyCustomHolder(view);
    }

    override fun onBindViewHolder(holder: RecentMovieAdapter.MyCustomHolder, position: Int) {
        var genreNames = getGenresNameOfMovie(liveData!![position].genre_ids, genreList!!)
        holder.bind(liveData!![position], genreNames)
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