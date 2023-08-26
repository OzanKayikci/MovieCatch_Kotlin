package com.example.moviecatch.ui.fragments.home.pages.moviedetailstabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviecatch.databinding.FragmentDetailsCastBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCastFragment : Fragment() {
    private var _binding: FragmentDetailsCastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsCastBinding.inflate(inflater)

        val view = binding.root

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}