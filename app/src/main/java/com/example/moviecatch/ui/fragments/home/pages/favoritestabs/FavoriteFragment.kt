package com.example.moviecatch.ui.fragments.home.pages.favoritestabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatch.R
import com.example.moviecatch.adapter.FavoriteMovieAdapter
import com.example.moviecatch.databinding.FragmentFavoriteBinding
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.viewmodal.FavoritesViewModel
import com.example.moviecatch.viewmodal.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment constructor(private val navController: NavController):Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter

    private val favoritesViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[FavoritesViewModel::class.java]
    }

    private val genreViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(GenreViewModel::class.java)
    }
    private var genreList: List<GenreData>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        initRecyclerViews()
        observerFunctions()
        return binding.root
    }

    private fun observerFunctions() {

        favoritesViewModel.getFavoritesObservable().observe(
            viewLifecycleOwner
        ) { t ->
            if (t != null) {
                favoriteMovieAdapter.setList(t, genreList!!)
            }
        }

        genreViewModel.getRecordsObservable().observe(viewLifecycleOwner) { t ->
            if (t != null) {
                genreList = t
                fetchMovies()

            }
        }
    }


    private fun initRecyclerViews() {

        val lmVertical =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)




        binding.recentRecyclerView.layoutManager = lmVertical
        favoriteMovieAdapter =
            FavoriteMovieAdapter(navController = navController)
        binding.recentRecyclerView.adapter = favoriteMovieAdapter
    }

    private fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            favoritesViewModel.getFavoriteMoviesFromDb()
        }
    }

}