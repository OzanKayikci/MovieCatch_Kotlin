package com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviecatch.ui.adapter.GenreAdapter
import com.example.moviecatch.databinding.FragmentDetailsAboutBinding
import com.example.moviecatch.databinding.FragmentMovieDetailsBinding
import com.example.moviecatch.models.Details
import com.example.moviecatch.viewmodal.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsAboutFragment constructor(private val details: Details) : Fragment() {

    private var _binding: FragmentDetailsAboutBinding? = null
    private val binding get() = _binding!!

    private lateinit var genreAdapter: GenreAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsAboutBinding.inflate(inflater)
        val view = binding.root
        Log.d("about", details.toString())

        initRecyclerViews()
        bindValues()
        return view
    }

    private fun bindValues() {

        genreAdapter.setList(details.genres)
        binding.overview.text = details.overview
        binding.releaseDate.text = details.release_date
        binding.txtVoteAverage.text = details.vote_average.toString() + "/ 10"
        binding.voteCount.text = "(${details.vote_count})"

    }

    private fun initRecyclerViews() {

        val lmHorizontal =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.genresRecycle.layoutManager = lmHorizontal
        genreAdapter = GenreAdapter()
        binding.genresRecycle.adapter = genreAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}