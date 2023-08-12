package com.example.moviecatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatch.R
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.models.Result
import javax.inject.Inject


class AllMoviesAdapter @Inject constructor(private val navController: NavController) :
    PagingDataAdapter<Result, AllMoviesAdapter.MyCustomHolder>(
        COMPARATOR
    ) {

    var genreList: List<GenreData>? = null;

    fun setList(genreList: List<GenreData>) {
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


    inner class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textTitle = view.findViewById<TextView>(R.id.title)
        private val txtGenre = view.findViewById<TextView>(R.id.txtGenre)
        private val posterView = view.findViewById<ImageView>(R.id.posterView)
        private val txtReleaseDate = view.findViewById<TextView>(R.id.txtReleaseDate)
        private val txtVoteAverage = view.findViewById<TextView>(R.id.txtVoteAverage)

        private  val detailsButton = view.findViewById<Button>(R.id.detailButton)
        fun bind(data: Result, genresName: String) {
            textTitle.text = data.title
            txtGenre.text = genresName
            txtReleaseDate.text = data.release_date
            txtVoteAverage.text = data.vote_average.toString() + "/ 10"
            Glide.with(posterView).load("https://image.tmdb.org/t/p/w342/${data.poster_path}")
                .into(posterView)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recent_movie_item, parent, false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        val movie = getItem(position)

        movie?.let {
            var genreNames = getGenresNameOfMovie(it.genre_ids, genreList!!)
            holder.bind(it, genreNames)
        }
        //bind to details page
        holder.itemView.setOnClickListener{

                val bundle = bundleOf("id" to movie?.id.toString())

                navController.navigate(R.id.action_allMoviesFragment_to_movieDetailsFragment,bundle)


        }

        holder.setIsRecyclable(false)
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}