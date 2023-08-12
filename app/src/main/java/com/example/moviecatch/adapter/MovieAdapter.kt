package com.example.moviecatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatch.R
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.models.Result

class MovieAdapter(private val isFirstScreen: Boolean = true, private  val navController: NavController) :
    RecyclerView.Adapter<MovieAdapter.MyCustomHolder>() {

    var liveData: List<Result>? = null
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

        private val textTitle = view.findViewById<TextView>(R.id.title)
        private val txtGenre = view.findViewById<TextView>(R.id.txtGenre)
        private val posterView = view.findViewById<ImageView>(R.id.posterView)

        fun bind(data: Result, genresName: String) {
            textTitle.text = data.title
            txtGenre.text = genresName
            Glide.with(posterView).load("https://image.tmdb.org/t/p/w342/${data.poster_path}")
                .into(posterView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_item, parent, false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        var genreNames = getGenresNameOfMovie(liveData!![position].genre_ids, genreList!!)
        val movie = liveData!![position]
        holder.bind(liveData!![position], genreNames)

        holder.itemView.setOnClickListener{

            val bundle = bundleOf("id" to movie?.id.toString())

            navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment,bundle)


        }
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