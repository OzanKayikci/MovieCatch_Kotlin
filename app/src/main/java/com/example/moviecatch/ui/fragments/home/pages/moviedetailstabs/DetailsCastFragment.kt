package com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatch.ui.adapter.CreditsAdapter
import com.example.moviecatch.ui.adapter.TrailersAdapter
import com.example.moviecatch.databinding.FragmentDetailsCastBinding
import com.example.moviecatch.viewmodal.MovieDetailsViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsCastFragment(private val movieId: Int) : Fragment() {
    private var _binding: FragmentDetailsCastBinding? = null
    private val binding get() = _binding!!
    private lateinit var creditsAdapter: CreditsAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[MovieDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsCastBinding.inflate(inflater)

        val view = binding.root
        initRecycleView()
        fetchCredits()
        observableFunctons()
        return view
    }

    private fun observableFunctons() {
        viewModel.getObservableMovieCredit().observe(viewLifecycleOwner) {
            if (it !== null) {
                creditsAdapter.setList(it.cast)
            }
        }
    }

    private fun fetchCredits() {
        lifecycleScope.launch {
            viewModel.getMovieCredits(movieId)
        }
    }

    private fun initRecycleView() {
        val lmVertical = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.castRecycle.layoutManager = lmVertical
        creditsAdapter = CreditsAdapter()
        binding.castRecycle.adapter = creditsAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}