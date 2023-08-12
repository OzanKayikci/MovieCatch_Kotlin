package com.example.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviecatch.databinding.FragmentMoviesPagesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesPagesFragment:Fragment() {
    private  var _binding:FragmentMoviesPagesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesPagesBinding.inflate(inflater,container,false)

        return  binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}