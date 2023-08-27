package com.example.moviecatch.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatch.R
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.di.dao.MovieData
import com.example.moviecatch.models.MovieResult
import com.example.moviecatch.ui.customviews.AddFavoritesButton

class FavoriteMovieAdapter(

    private val navController: NavController,
    ) :
    RecyclerView.Adapter<FavoriteMovieAdapter.MyCustomHolder>() {

    private var favoriteMovies: List<MovieData>? = null;
    var genreList: List<GenreData>? = null;

    fun setList(favoriteMovies: List<MovieData>, genreList: List<GenreData>) {
        this.favoriteMovies = favoriteMovies
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
            data: MovieData,
            genresName: String,

            ) {

            textTitle.text = data.title
            txtGenre.text = genresName
            txtReleaseDate.text = data.releaseDate
            txtVoteAverage.text = data.voteAverage.toString() + "/ 10"
            Glide.with(posterView).load("https://image.tmdb.org/t/p/w342/${data.posterPath}")
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
        var genreNames = getGenresNameOfMovie(favoriteMovies!![position].genresId, genreList!!)
        holder.bind(favoriteMovies!![position], genreNames)
        val movie = favoriteMovies!![position]

        holder.itemView.setOnClickListener {

            val bundle = bundleOf("id" to movie?.id.toString())

            navController.navigate(
                R.id.action_favoriteTabsFragment_to_movieDetailsFragment2,
                bundle
            )

        }


    }

    override fun getItemCount(): Int {
        if (favoriteMovies == null) {
            return 0
        }
        return favoriteMovies!!.size
    }


}