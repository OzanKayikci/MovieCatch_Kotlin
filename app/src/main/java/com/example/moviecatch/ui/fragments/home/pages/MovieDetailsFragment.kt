package com.example.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.moviecatch.databinding.FragmentMovieDetailsBinding
import com.example.moviecatch.viewmodal.HomePageViewModel
import com.example.moviecatch.viewmodal.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private var movieId: Int = 0
    private val viewModal by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[MovieDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        movieId = arguments?.getString("id")!!.toInt()
        fetchDetails(movieId)
        observerFunctions()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun observerFunctions() {
        viewModal.getObservableMovieDetails().observe(viewLifecycleOwner){
            if (it!=null){
                binding.title.text = it.title
                binding.overview.text = it.overview
                binding.releseDate.text = it.release_date
                binding.txtVoteAverage.text = it.vote_average.toString() + "/ 10"
                binding.voteCount.text = "(${it.vote_count})"
                Glide.with(binding.posterView).load("https://image.tmdb.org/t/p/w342/${it.backdrop_path}")
                    .into(binding.posterView)
            }
        }
    }
    private fun fetchDetails(id: Int) {
        lifecycleScope.launch {

            viewModal.getMovieDetails(id)

        }
    }
}