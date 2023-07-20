package com.example.moviecatch.ui.fragments.appintro.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentFifthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FifthScreen : Fragment() {

    private var _binding: FragmentFifthBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFifthBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val prevButton = activity?.findViewById<RelativeLayout>(R.id.prevButton)
        val nextButton = activity?.findViewById<RelativeLayout>(R.id.nextButton)

        val startButton  = binding.nextBtn

        nextButton?.alpha = 0f
        nextButton?.isEnabled = false

        prevButton?.setOnClickListener {
            viewPager?.currentItem = 3
        }


        startButton.setOnClickListener {
            
        }

        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}