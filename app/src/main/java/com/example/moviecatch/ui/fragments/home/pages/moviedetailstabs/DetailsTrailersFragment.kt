package com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatch.ui.adapter.TrailersAdapter
import com.example.moviecatch.databinding.FragmentDetailsTrailersBinding
import com.example.moviecatch.viewmodal.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsTrailersFragment(private val movieId: Int) : Fragment() {
    private var _binding: FragmentDetailsTrailersBinding? = null
    private val binding get() = _binding!!

    private lateinit var trailersAdapter: TrailersAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[MovieDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsTrailersBinding.inflate(inflater)

        val view = binding.root
        initRecycleView()
        fetchTrailers()
        observableFunctons()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observableFunctons() {
        viewModel.getObservableMovieTrailers().observe(viewLifecycleOwner) {
            if (it !== null) {
                trailersAdapter.setList(it.results)
            }
        }
    }

    private fun fetchTrailers() {
        lifecycleScope.launch {
            viewModel.getMovieTrailers(movieId)
        }
    }

    private fun initRecycleView() {
        val lmVertical = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.trailersRecycle.layoutManager = lmVertical
        trailersAdapter = TrailersAdapter()
        binding.trailersRecycle.adapter = trailersAdapter

    }
}