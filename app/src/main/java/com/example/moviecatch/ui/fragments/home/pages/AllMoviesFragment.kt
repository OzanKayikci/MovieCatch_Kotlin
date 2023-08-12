package com.example.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatch.adapter.AllMoviesAdapter
import com.example.moviecatch.adapter.MovieAdapter
import com.example.moviecatch.adapter.RecentMovieAdapter
import com.example.moviecatch.databinding.FragmentAllBinding
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.viewmodal.AllMoviesViewModel
import com.example.moviecatch.viewmodal.GenreViewModel
import com.example.moviecatch.viewmodal.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllMoviesFragment : Fragment() {


    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private lateinit var type: String
    private val viewModal by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[AllMoviesViewModel::class.java]
    }

    private val genreViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(GenreViewModel::class.java)
    }
    private lateinit var movieAdapter: AllMoviesAdapter

    private var genreList: List<GenreData>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllBinding.inflate(inflater, container, false)

        type = arguments?.getString("type")!!
        binding.moviesType.text = "$type Movies"

        val view = binding.root

        initRecyclerViews()
        observerFunctions()

        return view
    }

    private fun observerFunctions() {

        genreViewModel.getRecordsObservable().observe(viewLifecycleOwner) { t ->
            if (t != null) {
                genreList = t
                movieAdapter.setList(t)
                fetchMovies()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
           viewModal.getMoviesByPage(1,type).collect{
               movieAdapter.submitData(it)
           }
       }
    }

    private fun initRecyclerViews() {

        val lmVertical =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.allMoviesRecycler.layoutManager = lmVertical
        movieAdapter = AllMoviesAdapter(findNavController())
        binding.allMoviesRecycler.adapter = movieAdapter
    }

}