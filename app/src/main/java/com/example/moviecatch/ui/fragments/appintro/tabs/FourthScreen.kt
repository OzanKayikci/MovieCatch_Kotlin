package com.example.moviecatch.ui.fragments.appintro.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentFirstBinding
import com.example.moviecatch.databinding.FragmentFourthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FourthScreen:Fragment() {

    private var _binding: FragmentFourthBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFourthBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onResume() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val prevButton = activity?.findViewById<RelativeLayout>(R.id.prevButton)
        val nextButton = activity?.findViewById<RelativeLayout>(R.id.nextButton)
        nextButton?.alpha = 1f
        nextButton?.isEnabled = true

        nextButton?.setOnClickListener {
            viewPager?.currentItem = 4
        }

        prevButton?.setOnClickListener {
            viewPager?.currentItem = 2
        }
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}