package com.example.moviecatch.ui.adapter

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
import com.example.moviecatch.data.local.entities.GenreData
import com.example.moviecatch.models.MovieResult
import com.example.moviecatch.ui.customviews.AddFavoritesButton

class RecentMovieAdapter(
    private val isFirstScreen: Boolean = true,
    private val navController: NavController,

) :
    RecyclerView.Adapter<RecentMovieAdapter.MyCustomHolder>() {

   private var recentMovies: List<MovieResult>? = null;
    var genreList: List<GenreData>? = null;

    fun setList(recentMovies: List<MovieResult>, genreList: List<GenreData>) {
        this.recentMovies = recentMovies
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
        genresName = genresName.substring(0, genresName.length - 3)
        return genresName
    }

    class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val textTitle: TextView = view.findViewById<TextView>(R.id.title)
        private val txtGenre: TextView = view.findViewById<TextView>(R.id.txtGenre)
        private val posterView = view.findViewById<ImageView>(R.id.posterView)
        private val txtReleaseDate = view.findViewById<TextView>(R.id.txtReleaseDate)
        private val txtVoteAverage = view.findViewById<TextView>(R.id.txtVoteAverage)

        private val favoritesButton = view.findViewById<AddFavoritesButton>(R.id.favoriteButton)
        fun bind(
            data: MovieResult,
            genresName: String,

            ) {

            textTitle.text = data.title
            txtGenre.text = genresName
            txtReleaseDate.text = data.release_date
            txtVoteAverage.text = data.vote_average.toString() + "/ 10"
            Glide.with(posterView).load("https://image.tmdb.org/t/p/w342/${data.poster_path}")
                .into(posterView)

            favoritesButton.setFavorite(true)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCustomHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recent_movie_item, parent, false)

        return MyCustomHolder(view);
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        var genreNames = getGenresNameOfMovie(recentMovies!![position].genre_ids, genreList!!)
        holder.bind(recentMovies!![position], genreNames)
        val movie = recentMovies!![position]

        holder.itemView.setOnClickListener {

            val bundle = bundleOf("id" to movie?.id.toString())

            navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)

        }


    }

    override fun getItemCount(): Int {
        return if (recentMovies == null) {
            0
        } else {
            if (!isFirstScreen) {
                return recentMovies!!.size
            }
            8
        }
    }


}