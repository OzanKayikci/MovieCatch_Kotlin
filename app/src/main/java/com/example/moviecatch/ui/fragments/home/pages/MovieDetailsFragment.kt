package com.example.moviecatch.ui.fragments.home.pages

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.moviecatch.R
import com.example.moviecatch.ui.adapter.ViewPagerAdapter
import com.example.moviecatch.databinding.FragmentMovieDetailsBinding
import com.example.moviecatch.models.Details
import com.example.moviecatch.models.MovieResult
import com.example.moviecatch.ui.customviews.AddFavoritesButton
import com.example.moviecatch.ui.customviews.AddWatchlistButton

import com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs.DetailsAboutFragment
import com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs.DetailsCastFragment
import com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs.DetailsTrailersFragment
import com.example.moviecatch.viewmodal.FavoritesViewModel
import com.example.moviecatch.viewmodal.MovieDetailsViewModel

import com.google.android.material.tabs.TabLayoutMediator

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null

    private lateinit var favoriteButton: AddFavoritesButton
    private lateinit var watchlistButton: AddWatchlistButton

    private val binding get() = _binding!!
    private var movieId: Int = 0
    private val viewModal by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[MovieDetailsViewModel::class.java]
    }

    private val favoritesViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[FavoritesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        favoriteButton = binding.root.findViewById(R.id.add_favorites)
        watchlistButton = binding.root.findViewById(R.id.add_watchlist)

        movieId = arguments?.getString("id")!!.toInt()
        fetchDetails(movieId)
        observerFunctions()

        return view
    }


    private fun observerFunctions() {
        viewModal.getObservableMovieDetails().observe(viewLifecycleOwner) { it ->
            if (it != null) {


                binding.title.text = it.title

                Glide.with(binding.posterView)
                    .load("https://image.tmdb.org/t/p/w342/${it.poster_path}")
                    .into(binding.posterView)
                Glide.with(binding.posterView)
                    .load("https://image.tmdb.org/t/p/w342/${it.backdrop_path}")
                    .into(binding.backdropView)

                buttonsHandle(it)
                initTabLayout(it)

            }


        }
        favoritesViewModel.getChangedMovieObservable()
            .observe(viewLifecycleOwner) { localMovie ->
                if (localMovie != null) {
                    favoriteButton.setFavorite(localMovie.isFavorite)
                    watchlistButton.setWatchList(localMovie.isInWatchlist)
                } else {
                    favoriteButton.setFavorite(false)
                    watchlistButton.setWatchList(false)

                }
            }
        viewModal.getObservableMovieTrailers().observe(viewLifecycleOwner) {
//            initTrailerVideo(it.results[0].key)

        }
    }

    private fun fetchDetails(id: Int) {
        lifecycleScope.launch {

            val job1: Deferred<Unit> = async { viewModal.getMovieDetails(id) }
            val job2: Deferred<Unit> = async { viewModal.getMovieTrailers(id) }

            job1.await()
            job2.await()
        }
    }


    private fun initTabLayout(details: Details) {
        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.detailsViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "About"
                    1 -> tab.text = "Trailers"
                    2 -> tab.text = "Cast"
                }
            }
        val fragmentList = arrayListOf<Fragment>(
            DetailsAboutFragment(details),
            DetailsTrailersFragment(details.id),
            DetailsCastFragment(details.id),
        )
        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.detailsViewPager.adapter = adapter


        tabLayoutMediator.attach()

    }

    private fun buttonsHandle(details: Details) {

        favoritesViewModel.getMovieFromDb(details.id)

        favoriteButton.setOnClickListener {

            if (!favoriteButton.isFavorite) {
                favoritesViewModel.addMovieToDb(details, true) { response ->
                    if (response) {
                        favoriteButton.toggleFavorite()
                    }

                }

            } else {
                favoritesViewModel.deleteMovieFromDb(details.id, true) { response ->
                    if (response) {
                        favoriteButton.toggleFavorite()
                    }
                }
            }

        }

        watchlistButton.setOnClickListener {
            if (!watchlistButton.isWatchlist) {
                favoritesViewModel.addMovieToDb(details, false) { response ->
                    if (response) {
                        watchlistButton.toggleWatchlist()
                    }

                }

            } else {
                favoritesViewModel.deleteMovieFromDb(details.id, false) { response ->
                    if (response) {
                        watchlistButton.toggleWatchlist()
                    }
                }
            }

        }
    }

//    private fun initTrailerVideo(id:String) {
//        youTubePlayerView = binding.youtubePlayerView
//
    //        val iFramePlayerOptions = IFramePlayerOptions.Builder()
//            .controls(1)
//            .autoplay(0)
//            .fullscreen(1) // enable full screen button
//            .build()
//
//
//
//        youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                this@MovieDetailsFragment.youTubePlayer = youTubePlayer
//                youTubePlayer.loadVideo(id,0f)
//
////                val enterFullscreenButton = findViewById<Button>(R.id.enter_fullscreen_button)
////                enterFullscreenButton.setOnClickListener {
////                    youTubePlayer.toggleFullscreen()
////                }
//            }
//        }, iFramePlayerOptions)
//        lifecycle.addObserver(youTubePlayerView)
//
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}