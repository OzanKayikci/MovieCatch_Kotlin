package com.example.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatch.adapter.FavoriteMovieAdapter
import com.example.moviecatch.adapter.ViewPagerAdapter

import com.example.moviecatch.databinding.FragmentFavoriteTabsBinding
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.models.Details
import com.example.moviecatch.ui.fragments.home.pages.favoritestabs.FavoriteFragment
import com.example.moviecatch.ui.fragments.home.pages.favoritestabs.WatchlistFragment
import com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs.DetailsAboutFragment
import com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs.DetailsCastFragment
import com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs.DetailsTrailersFragment
import com.example.moviecatch.viewmodal.FavoritesViewModel
import com.example.moviecatch.viewmodal.GenreViewModel
import com.google.android.material.tabs.TabLayoutMediator

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteTabsFragment : Fragment() {

    private var _binding: FragmentFavoriteTabsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteTabsBinding.inflate(inflater, container, false)
        initTabLayout()
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}