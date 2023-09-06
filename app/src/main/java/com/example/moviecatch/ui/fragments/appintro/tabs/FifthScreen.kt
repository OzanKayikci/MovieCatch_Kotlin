package com.example.moviecatch.ui.fragments.appintro.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentFifthBinding
import com.example.moviecatch.data.local.entities.GenreData
import com.example.moviecatch.util.StringHelper
import com.example.moviecatch.viewmodal.GenreViewModel
import com.example.moviecatch.viewmodal.HomePageViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FifthScreen : Fragment() {

    private var _binding: FragmentFifthBinding? = null
    private val binding get() = _binding!!
    private lateinit var genreViewModel: GenreViewModel
    private lateinit var homePageViewModel: HomePageViewModel
    private lateinit var auth: FirebaseAuth
    private var stringHelper: StringHelper? = null
    private var genreList: MutableList<GenreData>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFifthBinding.inflate(inflater, container, false)
        val view = binding.root
        stringHelper = StringHelper()
        homePageViewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        genreViewModel = ViewModelProvider(this).get(GenreViewModel::class.java)
        auth = Firebase.auth
        setupGenreList()
        setupButtonClick()

        return view
    }


    override fun onResume() {
        super.onResume()
        setupNavigationButtons()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupGenreList() {
        genreList = mutableListOf()

        homePageViewModel.getObserverGenre().observe(viewLifecycleOwner) { genreResponse ->
            genreResponse?.let {
                for (item in it.genres) {
                    val trName = stringHelper!!.getTrName(item.name)
                    val genre = GenreData(0, item.id, item.name, trName)
                    genreList?.add(genre)
                }

                genreList?.let { list ->
                    genreViewModel.addAllGenres(list)
                    navigateToMainFragment()
                }
            }
        }

//        homePageViewModel.getObserverGenre().observe(viewLifecycleOwner) {
//            if (it != null) {
//                for (item in it.genres) {
//                    val trName = stringHelper!!.getTrName(item.name)
//                    val genre = GenreData(0, item.id, item.name, trName)
//                    genreList!!.add(genre)
//                }
//            }
//            genreViewModel.addAllGenres(genreList!!)
//            findNavController().navigate(R.id.action_appIntroFragment_to_mainFragment)
//        }
    }

    private fun setupButtonClick() {
        binding.nextBtn.setOnClickListener {
            homePageViewModel.loadGenreData()
        }
    }

    private fun setupNavigationButtons() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val prevButton = activity?.findViewById<RelativeLayout>(R.id.prevButton)
        val nextButton = activity?.findViewById<RelativeLayout>(R.id.nextButton)

        val startButton = binding.nextBtn

        nextButton?.alpha = 0f
        nextButton?.isEnabled = false

        prevButton?.setOnClickListener {
            viewPager?.currentItem = 3
        }
    }

    private fun navigateToMainFragment() {
        if (auth.currentUser !== null) {
            findNavController().navigate(R.id.action_appIntroFragment_to_mainFragment)

        } else
            findNavController().navigate(R.id.action_appIntroFragment_to_singInFragment)
    }
}