package com.example.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatch.R
import com.example.moviecatch.adapter.MovieAdapter
import com.example.moviecatch.adapter.RecentMovieAdapter
import com.example.moviecatch.databinding.ActivityMainBinding
import com.example.moviecatch.databinding.FragmentHomeBinding
import com.example.moviecatch.viewmodal.HomePageViewModal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recentMovieAdapter: RecentMovieAdapter

    private val viewModal by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[HomePageViewModal::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val view = binding.root

        initRecyclerViews()

        viewModal.getObserverLiveData(true).observe(
            viewLifecycleOwner
        ) { t ->
            if (t != null) {
                movieAdapter.setList(t.results)
            }
        }

        viewModal.getObserverLiveData(false).observe(
            viewLifecycleOwner
        ) { t ->
            if (t != null) {
                recentMovieAdapter.setList(t.results)
            }
        }
        fetchMovies()

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    private fun initRecyclerViews() {
        val lmHorizontal =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val lmVertical =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        binding.recycleView.layoutManager = lmHorizontal
        movieAdapter = MovieAdapter()
        binding.recycleView.adapter = movieAdapter


        binding.recentRecyclerView.layoutManager = lmVertical
        recentMovieAdapter = RecentMovieAdapter()
        binding.recentRecyclerView.adapter = recentMovieAdapter
    }

    fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val job1: Deferred<Unit> = async {
                viewModal.loadData("1", true)

            }
            val job2: Deferred<Unit> = async {
                viewModal.loadData("1", false)

            }

            job1.await()
            job2.await()
        }
    }
}