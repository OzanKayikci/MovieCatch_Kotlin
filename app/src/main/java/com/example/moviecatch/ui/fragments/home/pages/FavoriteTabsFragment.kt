package com.example.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviecatch.R
import com.example.moviecatch.ui.adapter.ViewPagerAdapter

import com.example.moviecatch.databinding.FragmentFavoriteTabsBinding
import com.example.moviecatch.data.remote.firebase.FirebaseResponse
import com.example.moviecatch.data.local.entities.MovieData
import com.example.moviecatch.ui.fragments.home.pages.favoritestabs.FavoriteFragment
import com.example.moviecatch.ui.fragments.home.pages.favoritestabs.WatchlistFragment
import com.example.moviecatch.viewmodal.FavoritesViewModel
import com.example.moviecatch.viewmodal.FirebaseViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteTabsFragment : Fragment() {

    private var _binding: FragmentFavoriteTabsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private var isExpanded = false
    private val favoritesViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[FavoritesViewModel::class.java]
    }
    private val firebaseViewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[FirebaseViewModel::class.java]
    }

    private val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_fab)
    }
    private val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_fab)
    }
    private val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clock_wise)
    }
    private val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anti_clock_wise)
    }
    private val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim)
    }
    private val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteTabsBinding.inflate(inflater, container, false)
        initTabLayout()
        buttonHandle()

        return binding.root
    }

    private fun initTabLayout() {
        val tabLayoutMediator =
            TabLayoutMediator(
                binding.tabLayoutFavorite,
                binding.favoritesViewPager
            ) { tab, position ->
                when (position) {
                    0 -> tab.text = "Favorites"
                    1 -> tab.text = "Watchlist"

                }
            }
        val fragmentList = arrayListOf<Fragment>(
            FavoriteFragment(findNavController()),
            WatchlistFragment(findNavController()),
        )
        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.favoritesViewPager.adapter = adapter


        tabLayoutMediator.attach()

    }

    private fun buttonHandle() {
        binding.fabFavorite.setOnClickListener {
            if (isExpanded) {
                shrinkFab()
            } else {
                expandFab()
            }

        }
        binding.fabPush.setOnClickListener {
            getStoredDataFromLocale()
            getObserverData { movies ->
                Log.d("inside observe", movies.toString())
                pushToFirebase(movies)
            }
        }

        binding.fabPull.setOnClickListener {
            getStoredDataFromLocale()

            getObserverData { movies ->
                Log.d("inside observe", movies.toString())
                fetchFromFirebase(movies)
            }
        }


    }

    private fun shrinkFab() {
        binding.transparentBg.startAnimation(toBottomBgAnim)
        binding.fabFavorite.startAnimation(rotateAntiClockWiseFabAnim)
        binding.fabPush.startAnimation(toBottomFabAnim)
        binding.fabPull.startAnimation(toBottomFabAnim)
        binding.tvPull.startAnimation(toBottomFabAnim)
        binding.tvPush.startAnimation(toBottomFabAnim)

        isExpanded = !isExpanded
    }

    private fun expandFab() {

        binding.transparentBg.startAnimation(fromBottomBgAnim)
        binding.fabFavorite.startAnimation(rotateClockWiseFabAnim)
        binding.fabPush.startAnimation(fromBottomFabAnim)
        binding.fabPull.startAnimation(fromBottomFabAnim)
        binding.tvPush.startAnimation(fromBottomFabAnim)
        binding.tvPull.startAnimation(fromBottomFabAnim)

        isExpanded = !isExpanded
    }

    private fun getObserverData(callback: (List<MovieData>) -> Unit) {

        favoritesViewModel.getAllStoredMoviesObserve().observe(viewLifecycleOwner) {
            Log.d("from viewmodel", it.toString())
            callback(it)
        }

    }

    private fun getStoredDataFromLocale() {
        lifecycleScope.launch {
            favoritesViewModel.getAllMoviesFromDb()
        }
    }

    private fun pushToFirebase(movies: List<MovieData>) {

        val database = Firebase.database
        firebaseViewModel.pushMoviesToFireBase(database, movies) { response, message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

        }
    }

    private fun fetchFromFirebase(existMovies: List<MovieData>) {
        val database = Firebase.database
        firebaseViewModel.fetchFromFirebase(database) { response ->
            when (response) {
                is FirebaseResponse.StringResponse -> {
                    Toast.makeText(requireContext(), response.stringValue, Toast.LENGTH_LONG).show()
                }

                is FirebaseResponse.MovieDataResponse -> {
                    saveToLocalFromFirebase(response.movieDataList, existMovies)
                }
            }
        }


    }

    private fun saveToLocalFromFirebase(movies: List<MovieData>, existMovies: List<MovieData>) {

        firebaseViewModel.saveToLocalFromFirebase(movies, existMovies) { response, newMovies ->
            if (!response) {
                Log.d("movies", "already up to date")
                Toast.makeText(requireContext(), "Movies already up up-to-date", Toast.LENGTH_LONG)
                    .show()
                return@saveToLocalFromFirebase
            }
            favoritesViewModel.addAllMovieToDb(newMovies!!) {
                if (it) {
                    Log.d("movies", newMovies.toString())

                    Toast.makeText(requireContext(), "Successfully Retrieved", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Storing Error", Toast.LENGTH_LONG).show()

                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}